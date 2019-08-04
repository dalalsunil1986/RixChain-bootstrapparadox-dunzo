var fs = require('fs')
  , gm = require('gm');
require('dotenv').config();
const express = require('express');
var myParser = require("body-parser");
var firebase = require("firebase");
const app = express();
var uniqid = require('uniqid');
var multer  = require('multer')
var upload = multer()
var upload = multer({ dest: 'uploads/' })

var file_name = '1.jpeg';
const sleep = (milliseconds) => {
  return new Promise(resolve => setTimeout(resolve, milliseconds))
}
app.use(myParser.json({extended : true}));


gm(file_name)
.size(function (err, size) {
  if (size.width > size.height){
    gm(file_name)
    .rotate('white',90)
    .quality(100)
    .write(`${file_name}`, function (err) {
    if (!err){//console.log("rotated")
      sleep(2000).then(()=>{
        gm(file_name)
        .resize(1080, 1920,'!')
        .quality(100)
        .write(`resized.jpeg`, function (err) {
      //if (!err){console.log("resized")}
    })
      });
    }
  })
  }
});


gm(file_name)
.resize(1080, 1920 , '!')
.quality(100)
.write(`resized.jpeg`, function (err) {
  //if (!err){console.log("resized")}
})


sleep(2000).then(()=>{
        gm('resized.jpeg')
.crop(1080, 1920, 0, 700 )
.quality(100)
.write(`cropped.jpeg`, function (err) {
  if (!err){//console.log('cropped')
sleep(2000).then(()=>{
        gm('cropped.jpeg')
.crop(1080, 1120, 0, -750 )
.quality(100)
.write(`cropped.jpeg`, function (err) {
  //if (!err){console.log('cropped1')}
})

sleep(500).then(()=>{
gm('resized.jpeg')
.crop(1080, 1920, 0, -1500 )
.quality(100)
.write(`shopName.jpeg`, function (err) {
  if (!err){console.log('name')}
    })

});

sleep(2000).then(()=>{
        gm('cropped.jpeg')
.crop(1080, 1120, -550, 0 )
.quality(100)
.write(`itemName.jpeg`, function (err) {
  //if (!err){console.log('cropped2')}
})



    gm('cropped.jpeg')
.crop(1080, 1120, 400, 0 )
.quality(100)
.write(`cropped1.jpeg`, function (err) {
  //if (!err){console.log('cropped3')}
    })
sleep(2000).then(()=>{
        gm('cropped.jpeg')
.crop(1080, 1120, -550, 0 )
.quality(100)
.write(`itemName.jpeg`, function (err) {
  //if (!err){console.log('cropped2') }
  })
  gm('cropped1.jpeg')
.crop(1080, 1120, -800, 0 )
.quality(100)
.write(`price.jpeg`, function (err) {
  //if (!err){console.log('cropped3')}
    })
     gm('cropped1.jpeg')
.crop(1080, 1120, -650, 0 )
.quality(100)
.write(`cropped1.jpeg`, function (err) {
  //if (!err){console.log('cropped4')}
})

sleep(2000).then(()=>{
    gm('cropped1.jpeg')
.crop(1080, 1120, 250, 0 )
.quality(100)
.write(`qty.jpeg`, function (err) {
  //if (!err){console.log('qty')}
    })

});




      });


      });





      });

}
})
      });



sleep(10000).then(()=>{
quickstart();
});







async function quickstart() {
  // Imports the Google Cloud client library
  console.log("processing")
  const vision = require('@google-cloud/vision');

// Creates a client
const client = new vision.ImageAnnotatorClient();

 //Item name
const fileName = 'itemName.jpeg';
var item_name = []
// Performs text detection on the local file
const [result] = await client.textDetection(fileName);
const detections = result.textAnnotations;
// detections.(text => console.log(text.description));
item_name = detections[0].description.split(/\r?\n/)

//Item Price
const fileName1 = 'price.jpeg';
var item_price = []
// Performs text detection on the local file
const [result1] = await client.textDetection(fileName1);
const detections1 = result1.textAnnotations;
// detections.(text => console.log(text.description));
item_price = detections1[0].description.split(/\r?\n/)

//Item Quantity
const fileName2 = 'qty.jpeg';
var item_qty = []
// Performs text detection on the local file
const [result2] = await client.textDetection(fileName2);
const detections2 = result2.textAnnotations;
// detections.(text => console.log(text.description));
item_qty = detections2[0].description.split(/\r?\n/)


 //Item name
const fileName3 = 'shopName.jpeg';
var shopName = []
// Performs text detection on the local file
const [result3] = await client.textDetection(fileName3);
const detections3 = result3.textAnnotations;
// detections.(text => console.log(text.description));
shopName = detections3[0].description.split(/\r?\n/)[0]
console.log(shopName)

for (var i =0 ; i < item_name.length ; i++) {

  console.log(item_name[i],item_qty[i],item_price[i]);


}







}










