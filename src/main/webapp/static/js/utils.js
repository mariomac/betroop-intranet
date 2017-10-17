function showDate(millis) {
    var date = new Date(millis);
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();

    var hours = date.getHours();
    var minutes = "0" + date.getMinutes();

    return day + "/" + month + "/" + year + " "
        + hours + ':' + minutes.substr(-2);
}