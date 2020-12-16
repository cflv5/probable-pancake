$(document).ready(function (){
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        const innerHTML = $("#menu-toggle").html() === "&lt;" ? "&gt;" : "&lt;";
        $("#wrapper").toggleClass("toggled");
        $("#menu-toggle").html(innerHTML);
    });
});