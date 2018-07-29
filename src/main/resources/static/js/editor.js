'use strict';

/**
 *初始化编辑器
 */
function init() {
    editormd.emoji.path = "http://www.webpagefx.com/tools/emoji-cheat-sheet/graphics/emojis/";
    return editormd("editormd", {
        width: "100%",
        height: 640,
        emoji: true,
        path: "../lib/editormd/lib/",
        saveHTMLToTextarea: true
    });
}

$(function () {
    let testEditor = init();
    $('#btn').click(function () {
        mdui.confirm(testEditor.getHTML(), '确认',
            function () {
                document.forms[0].submit();
            },
            function () {

            }
        );

    });
    //testEditor.getMarkdown();       // 获取 Markdown 源码
    //testEditor.getHTML();           // 获取 Textarea 保存的 HTML 源码
    //testEditor.getPreviewedHTML();  // 获取预览窗口里的 HTML，在开启 watch 且没有开启 saveHTMLToTextarea 时使用
});