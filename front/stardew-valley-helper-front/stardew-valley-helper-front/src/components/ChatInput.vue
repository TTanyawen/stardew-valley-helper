<template>
  <div class="chat-input-wrap">
    <div class="input-box">
      <textarea
        ref="textareaRef"
        v-model="text"
        :placeholder="disabled ? '请先选择或新建对话…' : '问问星露谷的事吧~'"
        :disabled="disabled || sending"
        rows="1"
        @keydown="onKeydown"
        @input="autoResize"
      />
      <button
        class="send-btn"
        :disabled="disabled || sending || !text.trim()"
        @click="submit"
      >
        {{ sending ? '…' : '发送' }}
      </button>
    </div>
    <p class="hint">Enter 发送 · Shift+Enter 换行</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'

defineProps({
  disabled: { type: Boolean, default: false },
  sending: { type: Boolean, default: false },
})

const emit = defineEmits(['send'])

const text = ref('')
const textareaRef = ref(null)

function autoResize() {
  const el = textareaRef.value
  if (!el) return
  el.style.height = 'auto'
  el.style.height = Math.min(el.scrollHeight, 160) + 'px'
}

function submit() {
  const content = text.value.trim()
  if (!content) return
  emit('send', content)
  text.value = ''
  if (textareaRef.value) {
    textareaRef.value.style.height = 'auto'
  }
}

function onKeydown(e) {
  if (e.key === 'Enter' && !e.shiftKey) {
    e.preventDefault()
    submit()
  }
}
</script>

<style scoped>
.chat-input-wrap {
  padding: 12px 24px 20px;
  background: linear-gradient(to top, var(--main-bg) 80%, transparent);
}

.input-box {
  max-width: 820px;
  margin: 0 auto;
  display: flex;
  align-items: flex-end;
  gap: 10px;
  background: var(--card-bg);
  border: 2px solid var(--border);
  border-radius: var(--radius);
  padding: 10px 12px 10px 16px;
  box-shadow: var(--shadow);
  transition: border-color 0.2s;
}

.input-box:focus-within {
  border-color: var(--sky-400);
}

textarea {
  flex: 1;
  border: none;
  resize: none;
  font-size: 0.9375rem;
  line-height: 1.5;
  color: var(--text-primary);
  background: transparent;
  max-height: 160px;
}

textarea::placeholder {
  color: var(--text-muted);
}

textarea:disabled {
  opacity: 0.6;
}

.send-btn {
  flex-shrink: 0;
  padding: 8px 18px;
  background: linear-gradient(135deg, var(--sky-400), var(--sky-500));
  color: white;
  font-weight: 700;
  font-size: 0.875rem;
  border-radius: var(--radius-sm);
  transition: opacity 0.2s;
}

.send-btn:hover:not(:disabled) {
  opacity: 0.9;
}

.send-btn:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}

.hint {
  max-width: 820px;
  margin: 6px auto 0;
  font-size: 0.75rem;
  color: var(--text-muted);
  text-align: center;
}
</style>
