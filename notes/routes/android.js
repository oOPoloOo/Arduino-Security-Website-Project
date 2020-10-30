var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
    var db = req.db;

    getAllNotes(db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].DataIrLaikas = getReadableDateAndTime(notes[i].DataIrLaikas);
            }
        }
        res.render('index', { title: 'U�ra�ai', data: notes });
    });
});


router.get('/getAllHacks', function(req, res) {
    var db = req.db;
    getAllHacks(db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].DataIrLaikas = getReadableDateAndTime(notes[i].DataIrLaikas);
            }
        }
        res.json(notes);
    });
});

router.get('/delAllHacks', function(req, res) {

    var db = req.db;
    deleteAllHacks(db, function(err) {
        if (err == null)
            console.log("Pa?alinta s?kmingai!");
        else
            console.log("?alinimo klaida." + err);
    });
});

router.get('/getAllHacksCount/:dFrom/:dTo', function(req, res) {
    var db = req.db;
    var dFrom = req.params.dFrom;
    var dTo = req.params.dTo;
    getAllHacksCount(dFrom, dTo, db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].Data = getReadableDateAndTime(notes[i].Data);
            }
        }
        res.json(notes);
    });
});
// router.get('/toNode/:time', function(req, res) {

//     var time = req.params.time;

//     console.Log(time);
//     // getAllHacksCount(dFrom, dTo, db, function(notes) {
//     //     if (notes != null) {
//     //         for (var i = 0; i < notes.length; i++) {
//     //             notes[i].Data = getReadableDateAndTime(notes[i].Data);
//     //         }
//     //     }
//     //     res.json(notes);
//     // });
// });




function getAllHacks(db, callback) {
    db.all('select * from isilauzimai', function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function getAllHacksCount(dateFrom, dateTo, db, callback) {
    db.all("SELECT strftime('%Y-%m-%d',DataIrLaikas) as Data, COUNT(*) as Kiekis From isilauzimai WHERE Data >= ? AND Data <= ? GROUP BY Data", dateFrom, dateTo, function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function deleteAllHacks(db, callback) {
    db.run("delete from isilauzimai", function(deleteError) {
        return callback(deleteError);
    });
}



router.get('/getAllNotes', function(req, res) {
    var db = req.db;
    getAllNotes(db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].DataIrLaikas = getReadableDateAndTime(notes[i].DataIrLaikas);
            }
        }
        res.json(notes);
    });
});
router.get('/getCategories', function(req, res) {
    var db = req.db;
    getCategories(db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].DataIrLaikas = getReadableDateAndTime(notes[i].DataIrLaikas);
            }
        }
        res.json(notes);
    });
});
router.get('/getSortASC', function(req, res) {
    var db = req.db;
    getSortASC(db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].DataIrLaikas = getReadableDateAndTime(notes[i].DataIrLaikas);
            }
        }
        res.json(notes);
    });
});
router.post('/getNames/:pav', function(req, res) {
    var db = req.db;

    getNames(pav, db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].DataIrLaikas = getReadableDateAndTime(notes[i].DataIrLaikas);
            }
        }
        res.json(notes);
    });
});

router.get('/getSelectedNotes/:pav', function(req, res) {

    var data = req.params.pav;
    var db = req.db;
    //console.log("Received variable: ");
    //console.log("Received variable: " + req.params.id);
    //console.log("Received variable: " + data);
    getSelectedNotes(data, db, function(notes) {
        if (notes != null) {
            for (var i = 0; i < notes.length; i++) {
                notes[i].DataIrLaikas = getReadableDateAndTime(notes[i].DataIrLaikas);
            }
        }
        res.json(notes);
    });
});

router.post('/delete/:id', function(req, res) {
    var id = req.params.id;
    var db = req.db;
    //console.log(id);
    getNote(id, db, function(note) {
        if (note != null) {
            deleteNote(id, db, function(rez) {
                var msg = "";
                if (rez == null) {
                    console.log(rez);
                    //msg = 'U�ra�as "' + note.Pavadinimas + '" i�trintas!';
                } else {
                    //msg = 'U�ra�as "' + note.Pavadinimas + '" nei�trintas!';
                    console.log("Nope");
                }
                res.json("OK");
                //res.render('note_delete', { title: 'U�ra�o trynimas', data: note, info: msg });
            });
        } else {
            res.json("NOPE");
            //res.render('note_delete', { title: 'U�ra�o trynimas', info: "U�ra�as nerastas!", note_id: 0 });
        }
    });
});

router.post('/edit/:id', function(req, res) {
    var db = req.db;

    var duom = req.params.id;
    var d = duom.split(";");

    var title = d[1]; //req.body.title;
    var cat = d[3]; //req.body.cat;
    var text = d[2]; //req.body.text;
    var note_id = d[0]; //req.body.note_id;
    var id = d[0]; //req.params.id;

    //console.log(title);
    //console.log(cat);
    //console.log(text);
    //console.log(note_id);
    //console.log(id);



    if (id != null && id == note_id) {
        getCategories(db, function(cats) {
            if (title.length > 0 && text.length > 0) {
                var timeStamp = new Date().getTime();
                //console.log("Time stamp: " + timeStamp);
                updateNote(note_id, title, cat, text, timeStamp, db, function(rez) {
                    if (rez == null) {
                        getNote(id, db, function(note) {
                            if (note != null) {
                                //res.render('note_edit', { title: 'U�ra�o redagavimas', data: note, cats: cats, info: "U�ra�as atnaujintas!" });
                                res.json("ok");
                            } else {
                                //res.render('note_edit', { title: 'U�ra�o redagavimas', info: "U�ra�as nerastas!" });
                                res.json("nera");
                            }
                        });
                    } else {
                        //res.render('note_edit', { title: 'U�ra�o redagavimas', cats: cats, info: "U�ra�as neatnaujintas!" });
                        res.json("no");
                    }
                });

            } else {
                //res.render('note_edit', { title: 'U�ra�o redagavimas', cats: cats, info: "�veskite tr�kstamus duomenis!" });
                res.json("truksta");
            }
        });
    } else {
        //res.render('note_edit', { title: 'U�ra�o redagavimas', info: "U�ra�as nerastas!" });
        res.json("nera");
    }
    //console.log(res);
});

router.get('/DelNote/:id', function(req, res) {
    var id = req.params.id;
    var db = req.db;
    deleteNote(id, db, function(err) {
        if (err == null)
            console.log("Pa�alinta s�kmingai!");
        else
            console.log("�alinimo klaida." + err);
    });
});

router.post('/getNote', function(req, res) {
    var id = req.body.id;
    var db = req.db;

    if (id === null || isNaN(id)) {
        res.status(501).send("Wrong parameter!");
    } else {
        getNote(id, db, function(note) {
            if (note != null) {
                note.DataIrLaikas = getReadableDateAndTime(note.DataIrLaikas);
                res.json(note);
            } else {
                res.status(500).send("Not found!");
            }
        });
    }
});

module.exports = router;

function updateNote(noteId, title, cat, text, timeStamp, db, callback) {
    db.run("update Uzrasas set KategorijosID = ?, Pavadinimas = ?, Tekstas = ?, AtnaujinimoDataIrLaikas = ? where ID = ? ; ", cat, title, text, timeStamp, noteId, function(updateError) {
        //jei nebuvo jokios klaidos updateError=null
        //jei buvo klaida updateError=klaidos_pranesimas
        return callback(updateError);
    });
}

function getAllNotes(db, callback) {
    db.all('select Uzrasas.Pavadinimas, Uzrasas.DataIrLaikas, Uzrasas.ID, Spalva.Kodas As Spalva, Kategorija.Pavadinimas As Kategorija, Uzrasas.Tekstas FROM Kategorija, Uzrasas, Spalva where Spalva.ID == Kategorija.SpalvosID and Uzrasas.KategorijosID = Kategorija.ID order by Uzrasas.Pavadinimas ASC ;', function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function getAllCats(db, callback) {
    db.all('select Kategorija.Pavadinimas, Kategorija.SpalvosID As Spalva;', function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function getSelectedNotes(data, db, callback) {
    //var d = Math.round(new Date(data + " 00:00:00.000").getTime());
    var d = data;
    console.log(d);
    db.all('select Uzrasas.Pavadinimas, Uzrasas.ID, Kategorija.Pavadinimas As Kategorija FROM Kategorija, Uzrasas where Uzrasas.KategorijosID = Kategorija.ID and Uzrasas.Pavadinimas == ? ;', d, function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function getNote(id, db, callback) {
    db.all('select Uzrasas.Pavadinimas, Uzrasas.DataIrLaikas, Uzrasas.Tekstas, Uzrasas.ID, Spalva.Kodas As Spalva, Kategorija.Pavadinimas As Kategorija, Kategorija.ID As KatID FROM Kategorija, Uzrasas, Spalva where Spalva.ID == Kategorija.SpalvosID and Uzrasas.KategorijosID = Kategorija.ID and Uzrasas.ID = ? ;', id, function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows[0]);
        }
    });
}

function deleteNote(id, db, callback) {
    db.run("delete from Uzrasas where ID = ? ", id, function(deleteError) {
        return callback(deleteError);
    });
}



function getSortASC(db, callback) {
    db.all("SELECT * from Uzrasas order by Uzrasas.Pavadinimas ASC", function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function getCategories(db, callback) {
    db.all('select * from Kategorija order by Pavadinimas;', function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function getNames(pav, db, callback) {
    db.all('select Kategorija.Pavadinimas Uzrasas.Kategorija FROM Uzrasas WHERE ? == Uzrasas.Kategorija;', pav, function(err, rows) {
        if (err) {
            console.log('*** Error serving querying database. ' + err);
            return callback(null);
        } else {
            return callback(rows);
        }
    });
}

function getReadableDateAndTime(timeStamp) {
    var tempDateTime = new Date(timeStamp);
    var years, months, days, hours, minutes, seconds;

    years = tempDateTime.getFullYear();

    var mnth = tempDateTime.getMonth() + 1; //Months start with 0 (january)

    if (mnth < 10) {
        months = '0' + mnth;
    } else {
        months = mnth;
    }

    var ds = tempDateTime.getDate();
    if (ds < 10) {
        days = '0' + ds;
    } else {
        days = ds;
    }

    var hr = tempDateTime.getHours();
    if (hr < 10) {
        hours = '0' + hr;
    } else {
        hours = hr;
    }

    var min = tempDateTime.getMinutes();
    if (min < 10) {
        minutes = '0' + min;
    } else {
        minutes = min;
    }

    var sec = tempDateTime.getSeconds();
    if (sec < 10) {
        seconds = '0' + sec;
    } else {
        seconds = sec;
    }
    var rez = '0';
    if (hr == 0 && min == 0 && sec == 0) rez = years + "-" + months + "-" + days;
    else rez = years + "-" + months + "-" + days + " " + hours + ":" + minutes + ":" + seconds;
    return rez;
}

module.exports.timeOut = "1";