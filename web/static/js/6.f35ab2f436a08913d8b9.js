webpackJsonp([6],{"6hIt":function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a={name:"btInfo",data:function(){return{value:[]}},created:function(){this.$axios.get("http://47.100.99.23:81/btCount").then(function(t){console.log(t.data)}).catch(function(t){console.log(t)})},mounted:function(){}},c={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"bt"},[n("div",{staticClass:"cont"},[n("label",[t._v(" 输入ip 查询历史bt下载记录")]),t._v(" "),n("div",{staticStyle:{"margin-top":"15px"}},[n("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入内容"},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}},[n("el-button",{attrs:{slot:"append",icon:"el-icon-search"},slot:"append"})],1)],1)])])},staticRenderFns:[]};var o=n("C7Lr")(a,c,!1,function(t){n("9Z5K")},"data-v-8aba5fbc",null);e.default=o.exports},"9Z5K":function(t,e){}});