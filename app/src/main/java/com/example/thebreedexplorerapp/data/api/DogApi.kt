package com.example.thebreedexplorerapp.data.api

import com.example.thebreedexplorerapp.data.model.ApiDogBreedGalleryResponse
import com.example.thebreedexplorerapp.data.model.ApiDogBreedsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

interface DogApi {
    suspend fun getAllBreeds(): ApiDogBreedsResponse
    suspend fun getBreedImages(breedKeyword: String): ApiDogBreedGalleryResponse
}

internal class DogApiImpl(private val client: HttpClient) : DogApi {

    override suspend fun getAllBreeds(): ApiDogBreedsResponse {
        // TODO remove when -> NO server error 520
        // client.get("https://dog.ceo/api/breeds/list/all").body<ApiDogBreedsResponse>()


        // mocked
        val jsonString =
            "{\"message\":{\"affenpinscher\":[],\"african\":[\"wild\"],\"airedale\":[],\"akita\":[],\"appenzeller\":[],\"australian\":[\"kelpie\",\"shepherd\"],\"bakharwal\":[\"indian\"],\"basenji\":[],\"beagle\":[],\"bluetick\":[],\"borzoi\":[],\"bouvier\":[],\"boxer\":[],\"brabancon\":[],\"briard\":[],\"buhund\":[\"norwegian\"],\"bulldog\":[\"boston\",\"english\",\"french\"],\"bullterrier\":[\"staffordshire\"],\"cattledog\":[\"australian\"],\"cavapoo\":[],\"chihuahua\":[],\"chippiparai\":[\"indian\"],\"chow\":[],\"clumber\":[],\"cockapoo\":[],\"collie\":[\"border\"],\"coonhound\":[],\"corgi\":[\"cardigan\"],\"cotondetulear\":[],\"dachshund\":[],\"dalmatian\":[],\"dane\":[\"great\"],\"danish\":[\"swedish\"],\"deerhound\":[\"scottish\"],\"dhole\":[],\"dingo\":[],\"doberman\":[],\"elkhound\":[\"norwegian\"],\"entlebucher\":[],\"eskimo\":[],\"finnish\":[\"lapphund\"],\"frise\":[\"bichon\"],\"gaddi\":[\"indian\"],\"german\":[\"shepherd\"],\"greyhound\":[\"indian\",\"italian\"],\"groenendael\":[],\"havanese\":[],\"hound\":[\"afghan\",\"basset\",\"blood\",\"english\",\"ibizan\",\"plott\",\"walker\"],\"husky\":[],\"keeshond\":[],\"kelpie\":[],\"kombai\":[],\"komondor\":[],\"kuvasz\":[],\"labradoodle\":[],\"labrador\":[],\"leonberg\":[],\"lhasa\":[],\"malamute\":[],\"malinois\":[],\"maltese\":[],\"mastiff\":[\"bull\",\"english\",\"indian\",\"tibetan\"],\"mexicanhairless\":[],\"mix\":[],\"mountain\":[\"bernese\",\"swiss\"],\"mudhol\":[\"indian\"],\"newfoundland\":[],\"otterhound\":[],\"ovcharka\":[\"caucasian\"],\"papillon\":[],\"pariah\":[\"indian\"],\"pekinese\":[],\"pembroke\":[],\"pinscher\":[\"miniature\"],\"pitbull\":[],\"pointer\":[\"german\",\"germanlonghair\"],\"pomeranian\":[],\"poodle\":[\"medium\",\"miniature\",\"standard\",\"toy\"],\"pug\":[],\"puggle\":[],\"pyrenees\":[],\"rajapalayam\":[\"indian\"],\"redbone\":[],\"retriever\":[\"chesapeake\",\"curly\",\"flatcoated\",\"golden\"],\"ridgeback\":[\"rhodesian\"],\"rottweiler\":[],\"rough\":[\"collie\"],\"saluki\":[],\"samoyed\":[],\"schipperke\":[],\"schnauzer\":[\"giant\",\"miniature\"],\"segugio\":[\"italian\"],\"setter\":[\"english\",\"gordon\",\"irish\"],\"sharpei\":[],\"sheepdog\":[\"english\",\"indian\",\"shetland\"],\"shiba\":[],\"shihtzu\":[],\"spaniel\":[\"blenheim\",\"brittany\",\"cocker\",\"irish\",\"japanese\",\"sussex\",\"welsh\"],\"spitz\":[\"indian\",\"japanese\"],\"springer\":[\"english\"],\"stbernard\":[],\"terrier\":[\"american\",\"andalusian\",\"australian\",\"bedlington\",\"border\",\"boston\",\"cairn\",\"dandie\",\"fox\",\"irish\",\"kerryblue\",\"lakeland\",\"norfolk\",\"norwich\",\"patterdale\",\"russell\",\"scottish\",\"sealyham\",\"silky\",\"tibetan\",\"toy\",\"welsh\",\"westhighland\",\"wheaten\",\"yorkshire\"],\"tervuren\":[],\"vizsla\":[],\"waterdog\":[\"spanish\"],\"weimaraner\":[],\"whippet\":[],\"wolfhound\":[\"irish\"]},\"status\":\"success\"}"

        val json = Json { ignoreUnknownKeys = true }
        val result = json.decodeFromString<ApiDogBreedsResponse>(jsonString)
        return result
    }

    override suspend fun getBreedImages(breedKeyword: String): ApiDogBreedGalleryResponse =
        client.get("https://dog.ceo/api/breed/$breedKeyword/images/random/20").body<ApiDogBreedGalleryResponse>()

}
