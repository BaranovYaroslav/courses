$("#uaLang").click(function(){
    $.get("/project/app/locale", {lang: 'ua'}).done(function() {
        location.reload();
    });
});

$("#enLang").click(function(){
    $.get("/project/app/locale", {lang: 'en'}).done(function() {
        location.reload();
    });
});