'use strict';
$(function () {
    /**
     * 用于设置夜间模式
     */
    $('#night_mode').click(function () {
        let $body = $('body');
        if ($('input[type="checkbox"]')[0].checked) {
            $body.addClass('mdui-theme-layout-dark');
        } else {
            $body.removeClass('mdui-theme-layout-dark');
        }
    })
});