import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

const USER_KEY = 'svh_user'

function loadUser() {
  try {
  const raw = localStorage.getItem(USER_KEY)
  return raw ? JSON.parse(raw) : null
  } catch {
    return null
  }
}

export const useUserStore = defineStore('user', () => {
  const saved = loadUser()
  const username = ref(saved?.username ?? '')
  const userId = ref(saved?.userId ?? 1)

  const isLoggedIn = computed(() => !!username.value)

  function persist() {
    localStorage.setItem(
      USER_KEY,
      JSON.stringify({ username: username.value, userId: userId.value }),
    )
  }

  function login(name, id) {
    username.value = name
    userId.value = id
    persist()
  }

  function logout() {
    username.value = ''
    userId.value = 1
    localStorage.removeItem(USER_KEY)
  }

  return { username, userId, isLoggedIn, login, logout }
})
