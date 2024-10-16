package com.markettwits.waifupics.random.report.mapper

import com.markettwits.waifupics.random.report.model.ImageReportUi
import com.markettwits.waifupics.result.NetworkResult

class ReportedImageMapper : NetworkResult.Mapper<Unit, ImageReportUi> {

    override fun map(item: Unit): ImageReportUi = ImageReportUi("Reported success \n Thanks")

    override fun map(errorMessage: String, code: Int) = ImageReportUi("Reported failed, try again")
}