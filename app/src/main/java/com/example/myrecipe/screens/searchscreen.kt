package com.example.myrecipe.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myrecipe.R
import com.example.myrecipe.utils.highlightSearch
import com.example.myrecipe.viewmodel.recipeviewmodel.Searchviewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable fun searchscreen(navcontroller:NavController,searchviewmodel: Searchviewmodel){
    var searchquery by rememberSaveable {
        mutableStateOf("")
    }
    var isfocus by rememberSaveable {
        mutableStateOf(false)

    }
    val bottomsheetstate= rememberBottomSheetScaffoldState()
    val scope= rememberCoroutineScope()

    val focusmanager= LocalFocusManager.current
    val keyboardcontroller= LocalSoftwareKeyboardController.current

    val searchresults=searchviewmodel.searchresults
    var title by remember {
        mutableStateOf("")
    }
    
    BottomSheetScaffold(sheetContent ={ bottomsheet(title=title){scope.launch { bottomsheetstate.bottomSheetState.collapse() }} }, scaffoldState = bottomsheetstate, sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp), sheetPeekHeight = 0.dp, sheetBackgroundColor = Color.White ) {
        Column (modifier = Modifier.fillMaxSize()){


        Row(modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(15.dp)){
            OutlinedTextField(value =searchquery , onValueChange ={ searchquery=it
                                                                  searchviewmodel.getSearchResults(it)} , modifier = Modifier
                .fillMaxSize()
                .onFocusChanged {
                    isfocus = it.isFocused
                }, textStyle = TextStyle(fontSize = 20.sp), placeholder = { Text(text = "Search any recipe", color = Color.LightGray, fontSize =18.sp )}, leadingIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.DarkGray, modifier = Modifier.clickable { navcontroller.popBackStack() })
            }, trailingIcon = {if (isfocus) Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.DarkGray, modifier = Modifier.clickable {focusmanager.clearFocus()
                keyboardcontroller?.hide()
                searchquery=""
            searchviewmodel.getSearchResults("")})}, singleLine =true,
                shape = RoundedCornerShape(15.dp), colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color(0xFFF2F7FD), unfocusedBorderColor = Color(0xFFE1E8EF), focusedBorderColor = Color(0xFFE1E8EF), cursorColor = Color.DarkGray)
            , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {keyboardcontroller?.hide()})
            )

        }
            if (searchviewmodel.isloading){
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFFE54900))
            }
            else {
                LazyColumn {

                    items(searchresults!!){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = R.drawable.searchresults), contentDescription = "", modifier = Modifier
                                .size(40.dp)
                                .padding(horizontal = 10.dp))
                            Text(text = highlightSearch(fulltext = it.title, query = searchquery), modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    keyboardcontroller?.hide()
                                    title = it.title
                                    scope.launch {
                                        bottomsheetstate.bottomSheetState.expand()
                                    }
                                }, fontSize = 20.sp)

                            
                            
                        }
                    }
                }
            }
        }
        if (bottomsheetstate.bottomSheetState.isExpanded){
            Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(alpha = 0.7f)){}
        }
        
    }

}