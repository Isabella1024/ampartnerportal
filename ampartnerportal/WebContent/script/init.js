<!--//--><![CDATA[//><!--

//settings
document.menuDelayTime = 200; // dropdown layer menu hide timer - milliseconds

/* added by JW for delay menu display */
function fnMenuShowHolder(divLayerID, divMenu) {
    document.pendingManuShow = divMenu.id;
    function tmp() {
        fnMenuShow(divLayerID, divMenu);
    }

    /* use closure to pass the divmenu object */
    clearTimeout(document.timerHideMenu);
    document.timerShowMenu = setTimeout(tmp, document.menuDelayTime);
}
/*end */

function fnMenuShow(divLayerID, divMenu) {
    if (document.pendingManuShow != divMenu.id) return;
    /* added by JW for delay menu display */
    var objLayer = document.getElementById(divLayerID);
    var objMenu = divMenu;
    fnMenuHideNow();

    document.activeLayer = objLayer;
    document.activeNav = objMenu
    if (objMenu.className) {
        document.activeNavClassName = objMenu.className;
    } else {
        document.activeNavClassName = "";
    }
    document.activeNav.className = "on";

    if (objLayer && objMenu) {
        var layerTop = objMenu.offsetTop + objMenu.offsetHeight;
        var layerLeft = objMenu.offsetLeft;
        var elm = objMenu.offsetParent;
        while (elm) {
            layerTop += elm.offsetTop;
            layerLeft += elm.offsetLeft;
            elm = elm.offsetParent;
        }
        objLayer.style.display = "block";
        objLayer.style.top = layerTop + "px";
        objLayer.style.left = layerLeft + onePxModifier + "px";
        fnIFrameHack(objLayer);
    } else {
        //debug - layer menu not found
        //alert("Invalid Layer ID or Menu Div - can't display menu");
    }

}

function fnIFrameHack(divObj) {
    if (document.all) {
        var f = document.getElementById("frameNavElement");
        f.style.width = divObj.offsetWidth;
        f.style.height = divObj.offsetHeight;
        f.style.top = divObj.style.top;
        f.style.left = divObj.style.left;
        f.style.display = "block";
    }
}

function fnMenuHide() {
    document.pendingManuShow = "";
    /* added by JW for delay menu display */
    fnClearMenuHideTimer();
    clearTimeout(document.timerShowMenu);
    document.timerHideMenu = setTimeout("fnMenuHideNow()", document.menuDelayTime);
}

function fnMenuHideNow() {
    clearTimeout(document.timerHideMenu);
    if (document.activeNav) document.activeNav.className = document.activeNavClassName;
    if (document.activeLayer) document.activeLayer.style.display = "none";

    var f = document.getElementById("frameNavElement");
    if (f) f.style.display = "none";
}

function fnClearMenuHideTimer() {
    clearTimeout(document.timerHideMenu);
}

function fnMenuShowChild(divID) {
    var objLayer = document.getElementById(divID);
    objLayer.style.display = "block";
    objLayer.style.left = "180px";
}
//--><!]]>