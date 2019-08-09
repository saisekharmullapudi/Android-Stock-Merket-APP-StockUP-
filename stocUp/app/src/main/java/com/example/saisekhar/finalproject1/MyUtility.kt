package com.example.saisekhar.finalproject1

import android.content.Context
import org.json.JSONObject
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException


class MyUtility {

    companion object {

        // Download an image using HTTP Get Request
        fun downloadImageusingHTTPGetRequest(urlString: String): Bitmap? {
            var image: Bitmap? = null

            try {
                val url = URL(urlString)
                val httpConnection = url.openConnection() as HttpURLConnection
                if (httpConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val stream = httpConnection.inputStream
                    image = getImagefromStream(stream)
                }
                httpConnection.disconnect()
            } catch (ex: Exception) {
                Log.d("MyDebugMsg", "Exception in sendHttpGetRequest")
                ex.printStackTrace()
            }

            return image
        }

        // Get an image from the input stream
        private fun getImagefromStream(stream: InputStream?): Bitmap? {
            var bitmap: Bitmap? = null
            if (stream != null) {
                bitmap = BitmapFactory.decodeStream(stream)
                try {
                    stream.close()
                } catch (e1: IOException) {
                    Log.d("MyDebugMsg", "IOException in getImagefromStream()")
                    e1.printStackTrace()
                }

            }
            return bitmap
        }


        // Download JSON data (string) using HTTP Get Request
        fun downloadJSONusingHTTPGetRequest(urlString: String): String? {
            var jsonString: String? = null
            Log.i("utrl Stirng", urlString.toString())
            try {
                val url = URL(urlString)

                Log.i("utrl", url.toString())

                val httpConnection = url.openConnection() as HttpURLConnection
                Log.i("connection",httpConnection.responseCode.toString())
                if (httpConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    Log.i("getting data","downloading data")
                    val stream = httpConnection.inputStream
                    jsonString = getStringfromStream(stream)
                } else {
                    Log.i("error", "print error");
                    jsonString = null
                }
                httpConnection.disconnect()

                /*
                with(url.openConnection() as HttpURLConnection){
                    requestMethod = "GET"

                    BufferedReader(InputStreamReader(inputStream)).use{
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while(inputLine != null){
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        Log.i("GET", "Movies: " + response.toString())
                    }
                }
                */
            } catch (e1: UnknownHostException) {
                Log.d("MyDebugMsg", "UnknownHostexception in downloadJSONusingHTTPGetRequest")
                e1.printStackTrace()
            } catch (ex: Exception) {
                Log.d("MyDebugMsg", ex.toString())
                ex.printStackTrace()
            }

            return jsonString
        }

        // Get a string from an input stream
        private fun getStringfromStream(stream: InputStream?): String? {
            var line: String
            var jsonString: String? = null
            if (stream != null) {
                val reader = BufferedReader(InputStreamReader(stream))
                val out = StringBuilder()
                try {
                    while (true) {
                        line = reader.readLine() ?: break
                        out.append(line)
                    }
                    reader.close()
                    jsonString = out.toString()
                } catch (ex: IOException) {
                    Log.d("MyDebugMsg", "IOException in downloadJSON()")
                    ex.printStackTrace()
                }

            }
            Log.i("jsonStirng", jsonString)
            return jsonString
        }

        // Load JSON string from a local file (in the asset folder)
        fun loadJSONFromAsset(context: Context, fileName: String): String? {
            var json: String? = null
            var line: String
            try {
                val stream = context.assets.open(fileName)
                if (stream != null) {

                    val reader = BufferedReader(InputStreamReader(stream))
                    val out = StringBuilder()
                    while (true) {
                        line = reader.readLine() ?: break
                        out.append(line)
                    }
                    reader.close()
                    json = out.toString()
                }
            } catch (ex: IOException) {
                Log.d("MyDebugMsg", "IOException in loadJSONFromAsset()")
                ex.printStackTrace()
            }

            return json
        }


        // Send json data via HTTP POST Request
        fun sendHttPostRequest(urlString: String, param: String): String? {
            var httpConnection: HttpURLConnection? = null
            Log.i("inside", "fucntion")
            var json: String? = null
//            try {
            val url = URL(urlString)
            Log.i("url value",urlString.toString())
            httpConnection = url.openConnection() as HttpURLConnection
//            Log.i("connection status",httpConnection.responseCode.toString())
            if (httpConnection != null) {
                httpConnection.setDoOutput(true)
                httpConnection.setChunkedStreamingMode(0)
                Log.i("param value", param.toString())
                val out = OutputStreamWriter(httpConnection.outputStream)
                Log.i("param ", param.toString())
                out.write(param)
                out.close()
//                Log.i("connection status",httpConnection.responseCode.toString())
                if (httpConnection.responseCode == HttpURLConnection.HTTP_OK) {
//                    Log.i("connection status",httpConnection.responseCode.toString())
                    val stream = httpConnection.inputStream
                    val reader = BufferedReader(InputStreamReader(stream))
                    var line: String
                    val msg = StringBuilder()
                    while (true) {
                        line = reader.readLine() ?: break
                        msg.append(line)
//                            Log.i("fbidvndiaod","irendtiojbopfs")
                        Log.d("MyDebugMsg:PostRequest", line)  // for debugging purpose
                    }

                    json = msg.toString()
                    reader.close()
                    Log.d("MyDebugMsg:PostRequest", "POST request returns ok")
                } else
                    Log.d("MyDebugMsg:PostRequest", "POST request returns error")
            }
//            } catch (ex: Exception) {
            /*  Log.d("MyDebugMsg", "Exception in sendHttpPostRequest")
              ex.printStackTrace()
          }finally {
              if (httpConnection != null)
                  httpConnection.disconnect()
          }*/
            return json
        }
    }
}
