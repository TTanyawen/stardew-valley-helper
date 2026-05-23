<template>
  <aside class="sidebar">
    <p class="sidebar-header">
      <span class="brand">🌾 Stardew Helper</span>
    </p>

    <button class="new-chat-btn" @click="$emit('create')">
      <span>＋</span> 新建对话
    </button>

    <nav class="group-list">
      <button
        v-for="group in groups"
        :key="group.id"
        class="group-item"
        :class="{ active: group.id === activeGroupId }"
        @click="$emit('select', group.id)"
      >
        <span class="group-icon">💬</span>
        <span class="group-title">{{ group.title || '未命名对话' }}</span>
      </button>
      <p v-if="!groups.length" class="empty-hint">还没有对话，点击上方新建吧~</p>
    </nav>

    <div class="sidebar-footer">
      <span class="username">{{ username }}</span>
      <button class="logout-btn" @click="$emit('logout')">退出</button>
    </div>
  </aside>
</template>

<script setup>
defineProps({
  groups: { type: Array, default: () => [] },
  activeGroupId: { type: Number, default: null },
  username: { type: String, default: '' },
})

defineEmits(['select', 'create', 'logout'])
</script>

<style scoped>
.sidebar {
  width: 260px;
  min-width: 260px;
  height: 100%;
  display: flex;
  flex-direction: column;
  background: var(--sidebar-bg);
  border-right: 2px solid var(--border);
}

.sidebar-header {
  padding: 20px 16px 12px;
}

.brand {
  font-weight: 700;
  font-size: 1rem;
  color: var(--text-primary);
}

.new-chat-btn {
  margin: 0 12px 12px;
  padding: 10px 14px;
  background: var(--card-bg);
  border: 2px dashed var(--sky-300);
  border-radius: var(--radius-sm);
  color: var(--sky-500);
  font-weight: 600;
  font-size: 0.875rem;
  display: flex;
  align-items: center;
  gap: 6px;
  transition: background 0.2s, border-color 0.2s;
}

.new-chat-btn:hover {
  background: var(--sky-50);
  border-color: var(--sky-400);
}

.new-chat-btn span {
  font-size: 1.1rem;
}

.group-list {
  flex: 1;
  overflow-y: auto;
  padding: 0 8px;
}

.group-item {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 12px;
  margin-bottom: 4px;
  border-radius: var(--radius-sm);
  background: transparent;
  color: var(--text-primary);
  font-size: 0.875rem;
  text-align: left;
  transition: background 0.15s;
}

.group-item:hover {
  background: var(--sidebar-hover);
}

.group-item.active {
  background: var(--sidebar-active);
  font-weight: 600;
}

.group-icon {
  flex-shrink: 0;
  font-size: 0.875rem;
}

.group-title {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.empty-hint {
  padding: 16px 12px;
  font-size: 0.8125rem;
  color: var(--text-muted);
  text-align: center;
  line-height: 1.5;
}

.sidebar-footer {
  padding: 12px 16px;
  border-top: 2px solid var(--border);
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
}

.username {
  font-size: 0.8125rem;
  color: var(--text-secondary);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.logout-btn {
  flex-shrink: 0;
  padding: 4px 10px;
  background: transparent;
  border: 1px solid var(--border);
  border-radius: 8px;
  color: var(--text-secondary);
  font-size: 0.75rem;
  transition: background 0.15s;
}

.logout-btn:hover {
  background: var(--sidebar-hover);
}
</style>
