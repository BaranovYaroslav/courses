$("#uaLang").click(function(){
    $.get("/pro/app/locale", {lang: 'ua'}).done(function() {
        console.log(1111);
        location.reload();
        console.log(1111);
    });
});

$("#enLang").click(function(){
    $.get("/pro/app/locale", {lang: 'en'}).done(function() {
        console.log(1111);
        location.reload();
        console.log(1111);
    });
});

function bra() {
    console.log("bra");
}