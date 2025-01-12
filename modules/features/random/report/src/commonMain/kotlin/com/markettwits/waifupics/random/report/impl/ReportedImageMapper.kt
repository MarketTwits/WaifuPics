package com.markettwits.waifupics.random.report.impl

import com.markettwits.waifupics.random.report.api.ReportImageState
import com.markettwits.waifupics.result.NetworkResult

class ReportedImageMapper : NetworkResult.Mapper<Unit, ReportImageState> {

    override fun map(item: Unit): ReportImageState = ReportImageState.Success("Reported success \n Thanks")

    override fun map(errorMessage: String, code: Int) = ReportImageState.Failed("Reported failed, try again")
}