<template>
  <div v-if="visible" class="overlay" @click.self="$emit('close')">
    <div class="dialog">
      <h3>新建对话</h3>
      <label class="field">
        <span>对话标题</span>
        <input v-model="title" type="text" placeholder="例如：NPC 问答" autofocus />
      </label>
      <label class="field">
        <span>系统提示词（可选）</span>
        <textarea
          v-model="prompt"
          rows="3"
          placeholder="例如：你是一个星露谷 NPC，请用角色口吻回答"
        />
      </label>
      <div class="actions">
        <button class="btn-cancel" @click="$emit('close')">取消</button>
        <button class="btn-confirm" :disabled="!title.trim() || loading" @click="confirm">
          {{ loading ? '创建中…' : '创建' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  visible: { type: Boolean, default: false },
})

const emit = defineEmits(['close', 'confirm'])

const title = ref('')
const prompt = ref('')
const loading = ref(false)

watch(
  () => props.visible,
  (v) => {
    if (v) {
      title.value = ''
      prompt.value = ''
      loading.value = false
    }
  },
)

async function confirm() {
  if (!title.value.trim()) return
  loading.value = true
  emit('confirm', { title: title.value.trim(), prompt: prompt.value.trim() })
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
  max-width: 420px;
  background: var(--card-bg);
  border-radius: 20px;
  padding: 28px;
  border: 2px solid var(--border);
  box-shadow: var(--shadow);
}

.dialog h3 {
  font-size: 1.125rem;
  margin-bottom: 20px;
  color: var(--text-primary);
}

.field {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-bottom: 14px;
}

.field span {
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--text-secondary);
}

.field input,
.field textarea {
  padding: 10px 14px;
  border: 2px solid var(--border);
  border-radius: var(--radius-sm);
  font-size: 0.9375rem;
  resize: vertical;
}

.field input:focus,
.field textarea:focus {
  border-color: var(--sky-400);
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 8px;
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
