package com.example.eliaitlay

import com.google.firebase.database.Exclude

data class Image(
   var imageUrl: String? = null,
   var nama: String? = null,
   var ket: String? = null,
   var desc: String? = null,
   @get:Exclude
   @set:Exclude
   var key: String? = null
)
