/**
 * 时间对象
 * @type {{getCurrentUnix: Time.getCurrentUnix, getTodayUnix: Time.getTodayUnix, getThisYearUnix: Time.getThisYearUnix, format: Time.format, compensateZero: Time.compensateZero, transform: Time.transform}}
 */
var Time = {
  //获取当前 Unix 时间戳
  getCurrentUnix: function () {
    return new Date().getTime();
  },
  //获取今日 0 点 0 分 0 秒的 Unix 时间戳
  getTodayUnix: function () {
    var date = new Date();
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date.getTime();
  },
  //获取今年 1 月 1 日 0 点 0 分 0 秒的 Unix 时间戳
  getThisYearUnix: function () {
    var date = new Date();
    date.setMonth(0);
    date.setDate(1);
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
    return date.getTime();
  },
  //格式化日期；输出格式为 xxxx-xx-xx
  format: function (val) {
    var dateObj = new Date(val);
    //month 代表月份的整数值从0（1月）到11（12月），所以需要转换
    var month = this.compensateZero(dateObj.getMonth() + 1);
    var day = this.compensateZero(dateObj.getDate());
    return dateObj.getFullYear() + '-' + month + '-' + day;
  },
  /**
   * 如果值小于 10，那么在前面补一个零
   * @param val
   * @returns {*}
   */
  compensateZero: function (val) {
    if (typeof val == 'number') {
      return val < 10 ? '0' + val : val;
    } else {
      return val;
    }
  },
  /**
   * 转换为相对时间
   *
   * 1 分钟之前，返回“刚刚”
   * 1 分钟到 1 小时之间，返回“xx 分钟前”
   * 1 小时到 1 天之间，返回“xx 小时前”
   * 1 天到 1 个月（假设固定为 31 天）之间，返回“xx 天前”
   * 大于 1 个月，返回“xx 年 xx 月 xx 日”
   * @param val unix 时间戳
   */
  transform: function (val) {
    //计算时间间隔（单位：s）
    console.log("getCurrentUnix:" + this.getCurrentUnix());
    var interval = (this.getCurrentUnix() - val) / 1000;
 
    if (Math.floor(interval / 60) <= 0) {//1 分钟之前
      return '刚刚';
    } else if (interval < 3600) {//1 分钟到 1 小时之间
      return Math.floor(interval / 60) + ' 分钟前';
    } else if (interval >= 3600 && (val - this.getTodayUnix() >= 0)) {//1 小时到 1 天之间
      return Math.floor(interval / 3600) + ' 小时前';
    } else if (interval / (3600 * 24) <= 31) {//1 天到 1 个月（假设固定为 31 天）之间
      return Math.ceil(interval / (3600 * 24)) + ' 天前';
    } else {
      return this.format(val);
    }
  }
 
};