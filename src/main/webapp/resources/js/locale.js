$("#uaLang").click(function(){
    $.get("/pro/app/locale", {lang: 'ua'}).done(function() {
        location.reload();
    });
});

$("#enLang").click(function(){
    $.get("/pro/app/locale", {lang: 'en'}).done(function() {
        location.reload();
    });
});