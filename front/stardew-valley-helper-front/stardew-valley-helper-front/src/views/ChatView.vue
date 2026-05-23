<template>
  <div class="layout">
    <Sidebar
      :groups="groups"
      :active-group-id="activeGroupId"
      :username="userStore.username"
      @select="selectGroup"
      @create="showCreate = true"
      @logout="handleLogout"
    />

    <main class="main">
      <header v-if="activeGroupId" class="header">
        <h2>{{ activeGroup()?.title || '对话' }}</h2>
        <button class="prompt-btn" @click="showPrompt = true">⚙ 系统提示词</button>
      </header>

      <ChatWindow
        :messages="messages"
        :streaming-content="streamingContent"
        :loading="loading"
        :active-group-id="activeGroupId"
      />

      <ChatInput
        :disabled="!activeGroupId"
        :sending="sending"
        @send="sendMessage"
      />
    </main>

    <CreateGroupDialog
      :visible="showCreate"
      @close="showCreate = false"
      @confirm="handleCreate"
    />

    <PromptDialog
      :visible="showPrompt"
      :initial-prompt="activeGroup()?.messageLevelSystemPrompt ?? ''"
      @close="showPrompt = false"
      @confirm="handlePromptSave"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { useChat } from '../composables/useChat'
import Sidebar from '../components/Sidebar.vue'
import ChatWindow from '../components/ChatWindow.vue'
import ChatInput from '../components/ChatInput.vue'
import CreateGroupDialog from '../components/CreateGroupDialog.vue'
import PromptDialog from '../components/PromptDialog.vue'

const router = useRouter()
const userStore = useUserStore()
const { userId } = storeToRefs(userStore)

const {
  groups,
  activeGroupId,
  messages,
  loading,
  sending,
  streamingContent,
  loadGroups,
  selectGroup,
  createGroup,
  updateSystemPrompt,
  sendMessage,
  activeGroup,
} = useChat(userId)

const showCreate = ref(false)
const showPrompt = ref(false)

onMounted(() => loadGroups())

async function handleCreate({ title, prompt }) {
  await createGroup(title, prompt)
  showCreate.value = false
}

async function handlePromptSave(prompt) {
  if (!activeGroupId.value) return
  await updateSystemPrompt(activeGroupId.value, prompt)
  showPrompt.value = false
}

function handleLogout() {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  display: flex;
  height: 100%;
}

.main {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
  background: var(--main-bg);
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 24px;
  border-bottom: 2px solid var(--border);
  background: rgba(255, 255, 255, 0.6);
  backdrop-filter: blur(8px);
}

.header h2 {
  font-size: 1rem;
  font-weight: 700;
  color: var(--text-primary);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.prompt-btn {
  flex-shrink: 0;
  padding: 6px 14px;
  background: var(--card-bg);
  border: 1px solid var(--border);
  border-radius: var(--radius-sm);
  color: var(--text-secondary);
  font-size: 0.8125rem;
  font-weight: 600;
  transition: background 0.15s;
}

.prompt-btn:hover {
  background: var(--sky-100);
}
</style>
