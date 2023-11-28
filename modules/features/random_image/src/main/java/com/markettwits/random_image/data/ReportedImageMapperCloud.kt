package com.markettwits.random_image.data

import com.markettwits.random_image.data.cloud.NetworkResult
import com.markettwits.random_image.presentation.components.report_image.ImageReportUi

class ReportedImageMapperCloud : NetworkResult.Mapper<Unit, ImageReportUi> {
    override fun map(item: Unit): ImageReportUi = ImageReportUi("Reported success \n Thanks")
    override fun map(errorMessage: String, code: Int) = ImageReportUi("Reported failed, try again")
}