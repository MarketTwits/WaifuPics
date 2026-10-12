const UPSTREAM = "https://api.nekosapi.com";
const CACHE_TTL_SECONDS = 2;

function allowedOrigins(env) {
  return new Set((env.ALLOWED_ORIGINS ?? "https://markettwits.github.io")
    .split(",")
    .map((origin) => origin.trim())
    .filter(Boolean));
}

function corsHeaders(origin, allowed) {
  const headers = new Headers({
    "Access-Control-Allow-Methods": "GET, POST, OPTIONS",
    "Access-Control-Allow-Headers": "Content-Type",
    "Access-Control-Max-Age": "86400",
    "Vary": "Origin",
  });

  if (allowed.has(origin)) {
    headers.set("Access-Control-Allow-Origin", origin);
  }

  return headers;
}

function jsonError(status, message, origin, allowed) {
  const headers = corsHeaders(origin, allowed);
  headers.set("Content-Type", "application/json; charset=utf-8");
  return new Response(JSON.stringify({ error: message }), { status, headers });
}

function isAllowedRequest(path, method) {
  return (path === "/v4/images/random" && method === "GET") ||
    (path === "/v4/images/report" && method === "POST");
}

function validParameters(path, searchParams) {
  if (path === "/v4/images/random") {
    const allowedKeys = new Set(["rating", "limit", "offset"]);
    for (const key of searchParams.keys()) {
      if (!allowedKeys.has(key)) return false;
    }

    const limit = Number(searchParams.get("limit") ?? "10");
    const rating = searchParams.get("rating") ?? "safe";
    return Number.isInteger(limit) && limit >= 1 && limit <= 25 &&
      /^[a-z,]{1,64}$/i.test(rating);
  }

  const id = searchParams.get("id");
  return searchParams.size === 1 && id !== null && /^\d{1,12}$/.test(id);
}

function withCors(response, origin, cacheControl, allowed) {
  const headers = new Headers(response.headers);
  for (const [key, value] of corsHeaders(origin, allowed)) headers.set(key, value);
  if (cacheControl) headers.set("Cache-Control", cacheControl);
  return new Response(response.body, {
    status: response.status,
    statusText: response.statusText,
    headers,
  });
}

export default {
  async fetch(request, env, ctx) {
    const url = new URL(request.url);
    const origin = request.headers.get("Origin") ?? "";
    const allowed = allowedOrigins(env);

    if (!allowed.has(origin)) {
      return jsonError(403, "Origin is not allowed", origin, allowed);
    }

    if (request.method === "OPTIONS") {
      return new Response(null, { status: 204, headers: corsHeaders(origin, allowed) });
    }

    if (!isAllowedRequest(url.pathname, request.method)) {
      return jsonError(404, "Endpoint is not allowed", origin, allowed);
    }

    if (!validParameters(url.pathname, url.searchParams)) {
      return jsonError(400, "Invalid query parameters", origin, allowed);
    }

    const upstreamUrl = new URL(url.pathname + url.search, UPSTREAM);
    const cache = caches.default;
    const cacheKey = new Request(upstreamUrl.toString(), { method: "GET" });

    if (request.method === "GET") {
      const cached = await cache.match(cacheKey);
      if (cached) return withCors(cached, origin, `public, max-age=0, s-maxage=${CACHE_TTL_SECONDS}`, allowed);
    }

    const upstreamHeaders = new Headers(request.headers);
    for (const key of [
      "origin",
      "referer",
      "host",
      "connection",
      "content-length",
      "access-control-request-method",
      "access-control-request-headers",
    ]) {
      upstreamHeaders.delete(key);
    }

    const upstreamRequest = new Request(upstreamUrl, {
      method: request.method,
      headers: upstreamHeaders,
      body: request.method === "GET" || request.method === "HEAD" ? undefined : request.body,
      redirect: "follow",
    });

    let upstream;
    try {
      upstream = await fetch(upstreamRequest);
    } catch {
      return jsonError(502, "Nekos API is unavailable", origin, allowed);
    }

    const response = withCors(
      upstream,
      origin,
      request.method === "GET" && upstream.ok
        ? `public, max-age=0, s-maxage=${CACHE_TTL_SECONDS}`
        : "no-store",
      allowed,
    );

    if (request.method === "GET" && upstream.ok) {
      ctx.waitUntil(cache.put(cacheKey, response.clone()));
    }

    return response;
  },
};
