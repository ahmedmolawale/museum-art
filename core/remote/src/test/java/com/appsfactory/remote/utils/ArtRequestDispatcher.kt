package com.appsfactory.remote.utils

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

internal class ArtRequestDispatcher {

    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                ART_SEARCH_REQUEST_PATH -> {
                    MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("response/art_search.json"))
                }

                ART_DETAILS_REQUEST_PATH -> {
                    MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("response/art_details.json"))
                }

                else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
            }
        }
    }

    /**
     * Return bad request response from mock server
     */
    internal inner class BadRequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest) =
            MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
    }

    /**
     * Return server error response from mock server
     */
    internal inner class ErrorRequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest) =
            MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }
}
