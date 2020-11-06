<template>
  <div id="app">
    <router-view v-if="isRouterShow"/>
  </div>
</template>

<script>
export default {
  name: 'app',
	provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterShow: true
    }
  },
  methods: {
    async reload () {
      this.isRouterShow = false
      await this.$nextTick()
      this.isRouterShow = true
    }
  }
}
</script>