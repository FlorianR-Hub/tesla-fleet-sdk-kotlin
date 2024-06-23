package com.boltfortesla.teslafleetsdk.net.api.vehicle.endpoints

import com.boltfortesla.teslafleetsdk.TeslaFleetApi.Region
import com.boltfortesla.teslafleetsdk.TeslaFleetApi.RetryConfig
import com.boltfortesla.teslafleetsdk.net.JitterFactorCalculator
import com.boltfortesla.teslafleetsdk.net.NetworkExecutorImpl
import okhttp3.OkHttpClient

/** Factory for [VehicleEndpoints]. */
internal class VehicleEndpointsFactory(
  private val jitterFactorCalculator: JitterFactorCalculator,
) {
  /**
   * Creates a [VehicleEndpoints] instance for the vehicle identified by [vehicleTag].
   *
   * @param vehicleTag the VIN or id field for the vehicle to make requests about
   * @param region the [Region] the API calls should be made against
   * @param retryConfig a [RetryConfig]
   * @param clientBuilder a pre-configured [OkHttpClient.Builder] that will be used when making
   *   network requests
   */
  fun create(
    vehicleTag: String,
    region: Region,
    retryConfig: RetryConfig,
    clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
  ): VehicleEndpoints {
    val networkExecutor = NetworkExecutorImpl(retryConfig, jitterFactorCalculator)

    return VehicleEndpointsImpl(
      vehicleTag,
      createVehicleEndpointsApi(region.baseUrl, clientBuilder),
      networkExecutor
    )
  }
}
