package com.example.listycity3

//import androidx.annotation.Nullable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    //Chatgpt was used to help me understand how to pass two city objects to updatecity hence the City,City
    onUpdateCity: (City,City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember {mutableStateOf<City?>(null)} // the var can either contain a city or contain nothing and intially there is nothign selected so null
    var updatedCityName by remember { mutableStateOf("") } // this will hold the cityname the user enters when editing
    var updatedProvinceName by remember { mutableStateOf("") } //this will hold the province the user enters when editing

    Column(modifier=modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("+")
            }
        }
if (showAddCityFields) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = newCityName,
            onValueChange = { newCityName = it },
            label = { Text("City") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = newProvinceName,
            onValueChange = { newProvinceName = it },
            label = { Text("Province") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            modifier = Modifier.padding(vertical = 12.dp),
            onClick = {
                if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                    onAddCity(
                        City(
                            name = newCityName,
                            province = newProvinceName
                        )
                    )
                    newCityName = ""
                    newProvinceName = ""
                    showAddCityFields = false
                }
            }
        ) {
            Text("Add City")
        }


    }
}
    if(selectedCity!=null){ // editing UI
       Row(
           modifier= Modifier.fillMaxWidth().padding(17.dp)){
               OutlinedTextField(value=updatedCityName, onValueChange = {updatedCityName=it},
                   label = {Text("Updated City")},
                   modifier= Modifier.weight(1f))

               Spacer(modifier= Modifier.width(8.dp))

               OutlinedTextField(
                   value = updatedProvinceName,
                   onValueChange = {updatedProvinceName=it},
                   label = {Text("Updated Province")},
                   modifier= Modifier.weight(1f)
               )
               Spacer(modifier = Modifier.width(8.dp))

           Button(
               onClick = {
                   if(updatedProvinceName.isNotBlank() && updatedCityName.isNotBlank()){

                       val updatedCity = City(province = updatedProvinceName, name = updatedCityName)

                       onUpdateCity(selectedCity!!,updatedCity) //since selectedcity was using ? means it can be null or not null so !! says that i guarantee the value is not null

                       selectedCity= null
                       updatedProvinceName =""
                       updatedCityName=""



                   }


               }
           ) {
               Text("Update City")
           }
           }
    }
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(cities) { index, city ->
            CityRow(city = city, onClick = {selectedCity=city  // after onclick runs, the selectedcity changes to the city that is clicked
            updatedCityName=city.name
            updatedProvinceName=city.province
            }
            )


            if (index < cities.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}}

@Composable
fun CityRow(city: City, onClick:() -> Unit) { // onClick:() -> Unit means that cityrow receives a fun and it should run when the row is clicked
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onClick()} // whenever the user clicks on the row, the ow is selected cause onclick runs
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ), onAddCity = {}
            , onUpdateCity = {oldCity,updatedCity ->}

        )
    }
}