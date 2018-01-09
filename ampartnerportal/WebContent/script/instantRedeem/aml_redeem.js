$( document ).ready(function(){

    // to hide and show package details
    $("#item_how_to h1 a").click(function(){
        $("#item_how_to_content").slideToggle();
        $(this).toggleClass( "btn_hide btn_show" );
      });
    $("#item_detail h1 a").click(function(){
        $("#item_detail_content").slideToggle();
        $(this).toggleClass( "btn_hide btn_show" );
      });
    $("#item_tc h1 a").click(function(){
        $("#item_tc_content").slideToggle();
        $(this).toggleClass( "btn_hide btn_show" );
      });
    //  placeholder plugin for IE 8
    $('input, textarea').placeholder();
    // for selectbox

});
