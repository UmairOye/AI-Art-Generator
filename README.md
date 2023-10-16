# AI Art Generator App

<img width="100" height="100" src="https://cdn.stablediffusionapi.com/generations/c9fc3626-12b8-4761-a271-1954d444c849-0.png" alt="Image" >

## Table of Contents

- [About the Project](#about-the-project)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Making API Requests](#making-api-requests)
  - [Loading Images](#loading-images)
- [Screenshots](#screenshots)
- [Contributing](#contributing)

## About the Project

The AI Art Generator App is an Android application that leverages the Stable Diffusion API to create unique artworks based on user-provided prompts. It follows the MVVM architecture pattern and uses Retrofit for API communication and Glide for efficient image loading.

## Getting Started

### Prerequisites

- Android Studio with Kotlin support
- Knowledge of MVVM architecture
- Retrofit and Glide dependencies added to your project

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/UmairOye/AI-Art-Generator.git
   cd AI-Art-Generator
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Usage
### Making API Requests

```bash
    val artApi: ArtApi by lazy {
          Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
              .create(ArtApi::class.java)
      }
  
  interface ArtApi {
      @POST("/api/v4/dreambooth")
      fun makeApiRequest(@Body requestBody: DreamBoothRequest): Call<ApiResponse>
  }
  
   fun makeApiRequest(requestBody: DreamBoothRequest, onResponse: (ApiResponse?) -> Unit) {
          val call = artApi.makeApiRequest(requestBody)
          call.enqueue(object : Callback<ApiResponse> {
              override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                  if(response.isSuccessful)
                  {
                      Log.d(TAG, "onResponse: ${response.body()!!.status}")
                      onResponse(response.body())
                  }else{
                      Log.d(TAG, "onResponse: not successful")
                  }
              }
  
              override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                  onResponse(ApiResponse(t.message.toString(), 0.0, 0, emptyList()))
              }
          })
      }

```
## Loading Images
Glide is used for efficient image loading. Here's an example of how to load an image into an ImageView:
```bash
Glide.with(this)
    .load("https://example.com/image.jpg")
    .into(imageView)
```
## Screenshots
<img width="150" height="300" src="https://github.com/UmairOye/AI-Art-Generator/blob/main/screenshots/Screenshot_20231016-161700.png" alt="Image" >   <img width="150" height="300" src="https://github.com/UmairOye/AI-Art-Generator/blob/main/screenshots/Screenshot_20231016-161715.png" alt="Image" >   <img width="150" height="300" src="https://github.com/UmairOye/AI-Art-Generator/blob/main/screenshots/Screenshot_20231016-161346.png" alt="Image" >


