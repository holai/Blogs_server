webpackJsonp([4],{SXuC:function(t,e){},pMgH:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i={nav_router:!0,isCollapse:!1};function l(){document.documentElement.clientWidth<763?i.isCollapse=!0:i.isCollapse=!1}l(),window.onresize=l;var o={name:"navAdmin",data:function(){return i},methods:{handleOpen:function(t,e){console.log(t,e)},handleClose:function(t,e){console.log(t,e)}},computed:{}},s={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("el-menu",{style:t.isCollapse?"width: 1.3rem;":"",attrs:{id:"admin-nav","default-active":t.$route.path,"background-color":"#545c64","text-color":"#fff","active-text-color":"#ffd04b",collapse:t.isCollapse,"collapse-transition":!1,router:t.nav_router},on:{open:t.handleOpen,close:t.handleClose}},[n("el-menu-item",{directives:[{name:"show",rawName:"v-show",value:!t.isCollapse,expression:"!isCollapse"}],attrs:{id:"title"}},[t._v("\n  \t\t\t\t  后台管理系统\n  \t\t\t  ")]),t._v(" "),t.isCollapse?n("el-menu-item",[n("span",{attrs:{slot:"title"},slot:"title"})]):t._e(),t._v(" "),n("el-menu-item",{attrs:{index:"/news"}},[n("i",{staticClass:"el-icon-setting"}),t._v(" "),n("span",{attrs:{slot:"title"},slot:"title"},[t._v("发布")])]),t._v(" "),n("el-menu-item",{attrs:{index:"/blog",route:{path:"/blog",query:{is:!0}}}},[n("i",{staticClass:"el-icon-document"}),t._v(" "),n("span",{attrs:{slot:"title"},slot:"title"},[t._v("文章管理")])]),t._v(" "),n("el-menu-item",{attrs:{index:"/aboutManage"}},[n("i",{staticClass:"el-icon-edit"}),t._v(" "),n("span",{attrs:{slot:"title"},slot:"title"},[t._v("个人信息")])])],1),t._v(" "),n("i",{class:t.isCollapse?"el-icon-right fh":"el-icon-back fh",on:{click:function(e){t.isCollapse=!t.isCollapse}}})],1)},staticRenderFns:[]};var a={name:"admin",data:function(){return{isCollapse:!0}},methods:{mover:function(){this.isCollapse=!1}},components:{navAdmin:n("C7Lr")(o,s,!1,function(t){n("vXnQ")},"data-v-4fd8f4d4",null).exports},created:function(){this.$axios.get("/login").then(function(t){0!=t.data.code&&(location.href="/#/login")}).catch(function(t){location.href="/#/login"})}},c={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"admin-body"},[e("navAdmin"),this._v(" "),e("router-view")],1)},staticRenderFns:[]};var r=n("C7Lr")(a,c,!1,function(t){n("SXuC")},null,null);e.default=r.exports},vXnQ:function(t,e){}});