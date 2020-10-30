/*** moduliu pareikalavimai ***/
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var routes = require('./routes/index');
var note = require('./routes/note');
var cats = require('./routes/cats');
var android = require('./routes/android');
var app = express();

var responses_x_questions = require('./reports/barChart.json');
var clone = require('clone');

var sqlite3 = require('sqlite3');

/*** db aprasymas ***/
var db = new sqlite3.Database('hacks.db');


var time = 3000;
// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

/*** db idejimas i req ***/
app.use(function(req, res, next) {
    req.db = db;
    next();
});

/*** route aprasymas ***/
app.use('/', routes);
app.use('/note', note);
app.use('/cat', cats);
app.use('/android', android);

app.get('/graph', function(req, res)
{
      var db = req.db;
      // var chartOptions = clone(responses_x_questions);
      // var categories = ["newCat1","newCat2","newCat3","newCat4","newCat5"];
      //
      //  chartOptions.xAxis[0].data = categories;
      //  chartOptions.series[0].data = [10,20,30,40,50];

      getAllHacksCount(db, function(data)
      {
   //      var options = {
   //      headers: {
   //     'x-timestamp': Date.now(),
   //     'x-sent': true,
   //     'name': 'MattDionis',
   //     'origin':'stackoverflow'
   // }
 // };
 var duomArray = generateDataPoints(data);
		   // res.render('graph_pirmas', { title: 'Express', data: JSON.stringify(chartOptions) });
            // res.render('graph', { title: 'Grafikas', data:data });
            res.render('graph0', { duomenys:duomArray });
              console.log("Ivykde /graph");
              //  res.sendFile('./graph.html',{root: __dirname});
                //res.sendFile(path.join(__dirname, 'index.html'), options);
      });
    //   res.sentFile('graph.html',{root: __dirname});

});
app.post('/siusti', function(req, res) {

    //time = req.params.time;
    console.log(req.body.laikas);

    openPort(req.body.laikas);

    //console.log(time);
});
app.get('/android/toNode/:time', function(req, res) {

    time = req.params.time;
    res.render('siuntLaikas', { laikas: time });
    openPort(time);

    console.log(time);
});

// app.post('/android/toNode/:time', function(req, res) {

//     var time = req.params.time;
//     res.render('siuntLaikas', { laikas: time });
// });

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});



// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});



module.exports = app;

var serialport = require('serialport');
var SerialPort = serialport.SerialPort;
var portName = process.argv[2];

console.log(android.timeOut);

var myPort = new SerialPort(portName, {
    baudRate: 9600,
    parser: serialport.parsers.readline("\r\n")
})

myPort.on('open', onOpen);
myPort.on('data', onData);


function onOpen() {
    console.log("Open connection");
}

function onData(data) {

    var today = new Date();
    var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    var dateTime = date + ' ' + time;


    console.log("Įsilaužta " + dateTime);


    db.run(`INSERT INTO "isilauzimai" (DataIrLaikas) VALUES (datetime('now'))`, function(err) {
        if (err) {
            return console.log(err.message);
        }

    });
}

function openPort(data) {

    console.log('port open');


    function sendData() {

        //  myPort.write(data.toString());


        for (var i = 0; i < data.length; i++) {
            myPort.write(new Buffer(data[i], 'ascii'), function(err, results) {
                // console.log('Error: ' + err);
                // console.log('Results ' + results);
            });
        }
        myPort.write(new Buffer('\n', 'ascii'), function(err, results) {
            // console.log('err ' + err);
            // console.log('results ' + results);
        });
        console.log('Sending ' + data.toString() + ' out the serial port');


    }

    // setInterval(sendData, 500);
    sendData();
}
function getBandymas(db, callback)
{
    db.all('select DataIrLaikas from isilauzimai;', function(err,rows)
    {
        if(err)
        {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        }
        else
        {
            return callback(rows);
        }
    });
}

function getAllHacksCount(db, callback) {
    db.all("SELECT strftime('%Y-%m-%d',DataIrLaikas) as Data, COUNT(*) as Kiekis From isilauzimai", function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}
function generateDataPoints(duomenys) {
  var arr = [];
    duomenys.forEach(function(element){
      arr.push({
          y: element.Kiekis,
          label: element.Data
      });

  })
  return arr;
}
