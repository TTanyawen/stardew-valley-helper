<template>
  <div v-if="visible" class="overlay" @click.self="$emit('close')">
    <div class="dialog">
      <h3>系统提示词</h3>
      <p class="desc">为该对话设置专属角色或回答风格</p>
      <textarea
        v-model="prompt"
        rows="5"
        placeholder="例如：你是一个熟悉星露谷的村民，请用温暖友好的语气回答"
      />
      <div class="actions">
        <button class="btn-cancel" @click="$emit('close')">取消</button>
        <button class="btn-confirm" :disabled="loading" @click="confirm">
          {{ loading ? '保存中…' : '保存' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  visible: { type: Boolean, default: false },
  initialPrompt: { type: String, default: '' },
})

const emit = defineEmits(['close', 'confirm'])

const prompt = ref('')
const loading = ref(false)

watch(
  () => props.visible,
  (v) => {
    if (v) {
      prompt.value = props.initialPrompt || ''
      loading.value = false
    }
  },
)

function confirm() {
  loading.value = true
  emit('confirm', prompt.value)
}
</script>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(30, 58, 95, 0.25);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}

.dialog {
  width: 100%;
  max-width: 480px;
  background: var(--card-bg);
  border-radius: 20px;
  padding: 28px;
  border: 2px solid var(--border);
  box-shadow: var(--shadow);
}

.dialog h3 {
  font-size: 1.125rem;
  margin-bottom: 6px;
}

.desc {
  font-size: 0.8125rem;
  color: var(--text-muted);
  margin-bottom: 14px;
}

textarea {
  width: 100%;
  padding: 12px 14px;
  border: 2px solid var(--border);
  border-radius: var(--radius-sm);
  font-size: 0.9375rem;
  line-height: 1.5;
  resize: vertical;
}

textarea:focus {
  border-color: var(--sky-400);
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 16px;
}

.btn-cancel,
.btn-confirm {
  padding: 8px 20px;
  border-radius: var(--radius-sm);
  font-weight: 600;
  font-size: 0.875rem;
}

.btn-cancel {
  background: var(--sky-50);
  color: var(--text-secondary);
  border: 1px solid var(--border);
}

.btn-confirm {
  background: linear-gradient(135deg, var(--sky-400), var(--sky-500));
  color: white;
}

.btn-confirm:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
