import router from '@/router'
import { useLoginUserStore } from '@/stores/useLoginUserStore.ts'
import { message } from 'ant-design-vue'

// 是否为首次获取登录用户
let firstFetchLoginUser = true;

router.beforeEach(async (to, from, next) => {
  const loginUserStore = useLoginUserStore()
  let loginUser = loginUserStore.loginUser
  //确保页面刷新时，首次加载时，能等待后端返回用户信息后再校验权限
  if (firstFetchLoginUser){
    await loginUserStore.fetchLoginUser();
    loginUser = loginUserStore.loginUser;
    firstFetchLoginUser = false;
  }
  const toUrl = to.fullPath
  //可以自定义权限校验规则，比如管理员材料访问 /admin 开头的页面
  if (toUrl.startsWith('/admin')) {
    if ((!loginUser || loginUser.userRole !== 'admin')){
      message.error('无权限访问！');
      next(`/user/login?redirect=${to.fullPath}`)
      return
    }
  }
  next();
})
