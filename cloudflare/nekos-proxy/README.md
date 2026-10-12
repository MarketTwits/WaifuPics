# Nekos API proxy

The Worker adds the CORS headers missing from `api.nekosapi.com` for the browser builds. Production only accepts the WaifuPics GitHub Pages origin. Wrangler's `development` environment accepts localhost origins for local end-to-end testing.

## Local test

From this directory, start the local Worker:

```sh
npx wrangler dev --env development --local --ip 127.0.0.1 --port 8787
```

The web app's local JS and Wasm configuration points to `http://127.0.0.1:8787/v4/`. In another terminal, run the Wasm browser app:

```sh
./gradlew :modules:instances:web:wasmJsBrowserDevelopmentRun
```

The Worker is only running on your computer during this test; this is not the production hosting model. Production is configured separately through the `NEKOS_PROXY_BASE_URL` GitHub Actions repository variable after deploying the Worker.

## Production wiring

After deploying this Worker to Cloudflare's free Workers plan, set the GitHub repository Actions variable `NEKOS_PROXY_BASE_URL` to the Worker URL ending in `/v4/`, for example `https://<worker>.<account>.workers.dev/v4/`. The deploy workflow writes that URL into both JS and Wasm distributions. Never use the localhost value for a Pages deployment.
