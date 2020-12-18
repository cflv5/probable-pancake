$(document).ready(function () {
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        const innerHTML = $("#wrapper").hasClass("toggled") ? `<em class="fas fa-arrow-left"></em>` : `<em class="fas fa-arrow-right">`;
        $("#wrapper").toggleClass("toggled");
        $("#menu-toggle").html(innerHTML);
    });
});