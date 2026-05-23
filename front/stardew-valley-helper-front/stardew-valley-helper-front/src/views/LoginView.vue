<template>
  <div class="login-page">
    <div class="login-card">
      <div class="logo">🌾</div>
      <h1 class="title">Stardew Valley Helper</h1>
      <p class="subtitle">星露谷问答小助手，陪你探索鹈鹕镇~</p>

      <form class="form" @submit.prevent="handleSubmit">
        <label class="field">
          <span>用户名</span>
          <input v-model="username" type="text" placeholder="请输入用户名" required />
        </label>
        <label class="field">
          <span>密码</span>
          <input v-model="password" type="password" placeholder="请输入密码" required />
        </label>

        <p v-if="error" class="error">{{ error }}</p>

        <button type="submit" class="btn-primary" :disabled="loading">
          {{ loading ? '请稍候…' : isRegister ? '注册' : '登录' }}
        </button>
      </form>

      <p class="toggle">
        {{ isRegister ? '已有账号？' : '还没有账号？' }}
        <a href="#" @click.prevent="isRegister = !isRegister">
          {{ isRegister ? '去登录' : '去注册' }}
        </a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login, register } from '../api/user'
import { useUserStore } from '../stores/user'

const router = useRouter()
const userStore = useUserStore()

const username = ref('')
const password = ref('')
const isRegister = ref(false)
const loading = ref(false)
const error = ref('')

async function handleSubmit() {
  error.value = ''
  loading.value = true
  try {
    if (isRegister.value) {
      const ok = await register(username.value, password.value)
      if (!ok) {
        error.value = '注册失败，用户名可能已存在'
        return
      }
      isRegister.value = false
      error.value = ''
      return
    }

    const userId = await login(username.value, password.value)
    if (userId <= 0) {
      error.value = '用户名或密码错误'
      return
    }
    userStore.login(username.value, userId)
    router.push('/')
  } catch {
    error.value = '网络错误，请确认后端已启动（端口 8087）'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(160deg, var(--sky-100) 0%, var(--sky-50) 50%, #ecfdf5 100%);
  padding: 24px;
}

.login-card {
  width: 100%;
  max-width: 400px;
  background: var(--card-bg);
  border-radius: 24px;
  padding: 40px 36px;
  box-shadow: var(--shadow);
  border: 2px solid var(--border);
  text-align: center;
}

.logo {
  font-size: 48px;
  margin-bottom: 8px;
}

.title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 6px;
}

.subtitle {
  font-size: 0.875rem;
  color: var(--text-secondary);
  margin-bottom: 28px;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 16px;
  text-align: left;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.field span {
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--text-secondary);
}

.field input {
  padding: 10px 14px;
  border: 2px solid var(--border);
  border-radius: var(--radius-sm);
  font-size: 0.9375rem;
  transition: border-color 0.2s;
}

.field input:focus {
  border-color: var(--sky-400);
}

.error {
  color: #ef4444;
  font-size: 0.875rem;
  text-align: center;
}

.btn-primary {
  margin-top: 4px;
  padding: 12px;
  background: linear-gradient(135deg, var(--sky-400), var(--sky-500));
  color: white;
  font-weight: 700;
  font-size: 1rem;
  border-radius: var(--radius-sm);
  transition: opacity 0.2s, transform 0.15s;
}

.btn-primary:hover:not(:disabled) {
  opacity: 0.92;
  transform: translateY(-1px);
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.toggle {
  margin-top: 20px;
  font-size: 0.875rem;
  color: var(--text-secondary);
}
</style>
