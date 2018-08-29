var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var expressWs = require('express-ws')(app);
const fileUpload = require('express-fileupload');

app.use(bodyParser.json());       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));


app.use(bodyParser.json());       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded()); // to support URL-encoded bodies
app.use(bodyParser());

app.use('/', express.static(__dirname));
app.use(fileUpload());

app.get('/', function (req, res) {
    res.send('Hello tensorflow app !');
});


app.listen(process.env.PORT || 80, function () {
    console.log('Goja node for tensorflow app listening on port ' + (process.env.PORT || 80));
})

app.post('/postImage', function (req, res) {
    try {
        console.log("Received image from the andriod phone");

        var fileStr = req.body.sampleFile.toString();

        var b = new Buffer(fileStr, 'base64')

        var fs = require('fs');
        fs.writeFile("D:\\UPS_Hack\\hack.jpg", b, function (err) {
            if (err) {
                return console.log(err);
            }

            console.log("The file was saved!");
        });
        

        //call the tensor flow script
        var exec = require('child_process').exec;
        //var cmd = 'python /tf_files/is_image_a_package_node.py /tf_files/test_images/test_file.jpg';
        var cmd = 'D:\\UPS_Hack\\test.exe';

        var spin_wait = true;
        exec(cmd, function (error, stdout, stderr) {
            console.log('py script results = ' + stdout);
            spin_wait = false;
            var arr = stdout.split('\n');
            //var ret_val = { 'result': arr.join(';') };
            //var str_val = JSON.stringify(ret_val);
            
            res.send(arr.join(';'));
        });

       // while (spin_wait)
        //{
            //console.log('waiting for tensorflow python script to process the request..');
        //}

        //res.send("{'result':std_out}");

    } catch (e) {
        console.log(e.toString());
    }
});
