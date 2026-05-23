import { ref } from 'vue'
import {
  getAllMessageGroups,
  getMessageDetails,
  createMessageGroup,
  setSystemPrompt,
} from '../api/message'
import { chatStream } from '../api/chat'

export function useChat(userId) {
  const groups = ref([])
  const activeGroupId = ref(null)
  const messages = ref([])
  const loading = ref(false)
  const sending = ref(false)
  const streamingContent = ref('')

  async function loadGroups() {
    groups.value = await getAllMessageGroups(userId.value)
    groups.value.sort((a, b) => b.id - a.id)
  }

  async function selectGroup(groupId) {
    activeGroupId.value = groupId
    loading.value = true
    try {
      messages.value = await getMessageDetails(groupId)
    } finally {
      loading.value = false
    }
  }

  async function createGroup(title, systemPrompt) {
    await createMessageGroup(userId.value, title, systemPrompt || '')
    await loadGroups()
    const newest = groups.value[0]
    if (newest) await selectGroup(newest.id)
  }

  async function updateSystemPrompt(groupId, prompt) {
    await setSystemPrompt(groupId, prompt)
    await loadGroups()
  }

  function sendMessage(content) {
    if (!activeGroupId.value || !content.trim() || sending.value) return

    const groupId = activeGroupId.value
    messages.value.push({ role: 'user', content: content.trim() })
    sending.value = true
    streamingContent.value = ''

    chatStream(groupId, content.trim(), {
      onChunk(chunk) {
        streamingContent.value += chunk
      },
      onDone() {
        if (streamingContent.value) {
          messages.value.push({ role: 'model', content: streamingContent.value })
        }
        streamingContent.value = ''
        sending.value = false
      },
      onError() {
        streamingContent.value = ''
        sending.value = false
      },
    })
  }

  const activeGroup = () =>
    groups.value.find((g) => g.id === activeGroupId.value) ?? null

  return {
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
  }
}
