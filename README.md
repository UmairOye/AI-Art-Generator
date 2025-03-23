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

## About the Project

The AI Art Generator App is an Android application that leverages the Stable Diffusion API to create unique artworks based on user-provided prompts. It follows the Clean Architectuer pattern and uses Retrofit for API communication, flows for API states and Glide for efficient image loading.

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
## Retrofit Client

```bash
    @Module
@InstallIn(SingletonComponent::class)
object RetrofitInjection {

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
       return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
  
  
```
## API Service

  ```bash
  interface ApiService {
    @POST(BuildConfig.END_POINT)
    fun makeApiRequest(@Body requestBody: DreamBoothRequest): Call<DreamBoothResponse>
}
```

## Make API Request

```bash
override fun makeApiRequest(requestBody: DreamBoothRequest): Flow<RequestState<MetaData?>> =
        flow {
            emit(RequestState.Loading)

            try {
                val response = suspendCoroutine { continuation ->
                    artApi.makeApiRequest(requestBody)
                        .enqueue(object : Callback<DreamBoothResponse> {
                            override fun onResponse(
                                call: Call<DreamBoothResponse>,
                                response: Response<DreamBoothResponse>
                            ) {
                                continuation.resume(response)
                            }

                            override fun onFailure(call: Call<DreamBoothResponse>, t: Throwable) {
                                continuation.resumeWithException(t)
                            }
                        })
                }

                if (response.isSuccessful && response.body() != null) {
                    emit(RequestState.Success(response.body()!!.meta))
                    Log.d(TAG, "onResponse: ${response.body()?.meta}")
                } else {
                    emit(RequestState.Error(Exception("Response not successful")))
                    Log.d(TAG, "onResponse: not successful")
                }

            } catch (e: Exception) {
                emit(RequestState.Error(e))
                Log.d(TAG, "onFailure: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)

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


## Don't Forget to Star ⭐

If you found this project useful or had fun exploring it, please consider giving it a star. It's a great way to show your appreciation and it puts a smile on my face! 😊🌟


