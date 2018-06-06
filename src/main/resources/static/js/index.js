(function(){

    window.onload = function () {
        //（jquery）
        $('#note').keyup(function () {
            var len = this.value.length
            $('#text-count2').text(len);

        })
    }
})()