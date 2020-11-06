<template>
  <div class="headerbox">
    <!--top begin-->
    <header id="header">
      <div class="navbox">
        <h2 id="mnavh"><span class="navicon"></span></h2>
        <div class="logo"><a href="/">Koi 博客 | <font style="font-size: 16px;">学如逆水行舟，不进则退</font></a></div>
        <nav>
          <ul id="starlist" >
            <li>
              <a href="/">网站首页</a>
            </li>
            <li>
              <a href="/blog" >技术博客</a>
            </li>
            <li><a href="list3.html">在线教程</a></li>
            <li class="menu"><a href="list1.html">在线工具</a>
              <ul class="sub">
                <li><a href="#">二维码生成</a></li>
                <li><a href="#">图片处理</a></li>
              </ul>
              <span></span>
            </li>
            <li><a href="daohang.html">留言板</a></li>
            <li><a href="/about">关于我</a></li>
          </ul>
        </nav>
        <div class="searchbox">
          <div class="search">
            <form action="/e/search/index.php" method="post" name="searchform" id="searchform">
              <input name="keyboard" id="keyboard" class="input_text" value="请输入关键字词" style="color: rgb(153, 153, 153);"
                onFocus="if(value=='请输入关键字词'){this.style.color='#000';value=''}" onBlur="if(value==''){this.style.color='#999';value='请输入关键字词'}"
                type="text">
              <input name="show" value="title" type="hidden">
              <input name="tempid" value="1" type="hidden">
              <input name="tbname" value="news" type="hidden">
              <input name="Submit" class="input_submit" value="搜 索" type="submit">
            </form>
          </div>
          <div class="searchclose"></div>
        </div>
      </div>
    </header>

    <!--top end-->
  </div>
</template>
<style>
  .headerbox{
    height: 60px;
  }
   #header {
      position: fixed;
      transform: translateY(-10px);
      z-index: 999;
      }
</style>
