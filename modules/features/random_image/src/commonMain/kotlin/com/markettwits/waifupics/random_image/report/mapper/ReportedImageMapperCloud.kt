package com.markettwits.waifupics.random_image.report.mapper

import com.markettwits.cloud_datasource.data.cloud.NetworkResult
import com.markettwits.waifupics.random_image.report.components.ImageReportUi

class ReportedImageMapperCloud : NetworkResult.Mapper<Unit, ImageReportUi> {

    override fun map(item: Unit): ImageReportUi = ImageReportUi("Reported success \n Thanks")

    override fun map(errorMessage: String, code: Int) = ImageReportUi("Reported failed, try again")
}