package com.markettwits.waifupics.cloud_datasource.data

import com.markettwits.cloud_datasource.data.cloud.NetworkResult
import com.markettwits.cloud_datasource.presentation.random_image.components.report_image.ImageReportUi

class ReportedImageMapperCloud : NetworkResult.Mapper<Unit, ImageReportUi> {

    override fun map(item: Unit): ImageReportUi = ImageReportUi("Reported success \n Thanks")

    override fun map(errorMessage: String, code: Int) = ImageReportUi("Reported failed, try again")
}