<template>
  <div class="chat-window" ref="containerRef">
    <div v-if="loading" class="loading">加载消息中…</div>

    <div v-else-if="!activeGroupId" class="welcome">
      <div class="welcome-icon">🌻</div>
      <h2>欢迎来到 Stardew Valley Helper</h2>
      <p>选择左侧对话，或新建一个开始提问吧~</p>
    </div>

    <template v-else>
      <div
        v-for="(msg, i) in messages"
        :key="i"
        class="message-row"
        :class="msg.role === 'user' ? 'user' : 'assistant'"
      >
        <div class="avatar">{{ msg.role === 'user' ? '🧑‍🌾' : '🤖' }}</div>
        <div class="bubble">
          <MarkdownContent
            :content="msg.content"
            :variant="msg.role === 'user' ? 'user' : 'assistant'"
           />
        </div>
      </div>

      <div v-if="streamingContent" class="message-row assistant">
        <div class="avatar">🤖</div>
        <div class="bubble streaming">
          <MarkdownContent :content="streamingContent" variant="assistant" />
          <span class="cursor">▍</span>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, watch, nextTick } from 'vue'
import MarkdownContent from './MarkdownContent.vue'

const props = defineProps({
  messages: { type: Array, default: () => [] },
  streamingContent: { type: String, default: '' },
  loading: { type: Boolean, default: false },
  activeGroupId: { type: Number, default: null },
})

const containerRef = ref(null)

watch(
  () => [props.messages.length, props.streamingContent],
  async () => {
    await nextTick()
    if (containerRef.value) {
      containerRef.value.scrollTop = containerRef.value.scrollHeight
    }
  },
)
</script>

<style scoped>
.chat-window {
  flex: 1;
  overflow-y: auto;
  padding: 24px 0;
}

.loading,
.welcome {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: var(--text-secondary);
  gap: 8px;
}

.welcome-icon {
  font-size: 56px;
  margin-bottom: 8px;
}

.welcome h2 {
  font-size: 1.25rem;
  color: var(--text-primary);
}

.welcome p {
  font-size: 0.9375rem;
}

.message-row {
  display: flex;
  gap: 12px;
  padding: 12px 24px;
  max-width: 820px;
  margin: 0 auto;
}

.message-row.user {
  flex-direction: row-reverse;
}

.avatar {
  flex-shrink: 0;
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
  background: var(--sky-100);
  border-radius: 50%;
}

.user .avatar {
  background: var(--green-400);
}

.bubble {
  padding: 12px 16px;
  border-radius: var(--radius);
  max-width: calc(100% - 48px);
  overflow: hidden;
}

.assistant .bubble {
  background: var(--card-bg);
  border: 1px solid var(--border);
  box-shadow: var(--shadow);
}

.user .bubble {
  background: linear-gradient(135deg, var(--sky-300), var(--sky-400));
  color: white;
}

.streaming {
  position: relative;
}

.streaming .cursor {
  display: inline-block;
  margin-left: 2px;
  color: var(--sky-400);
  vertical-align: text-bottom;
  animation: blink 0.8s step-end infinite;
}

@keyframes blink {
  50% { opacity: 0; }
}
</style>
