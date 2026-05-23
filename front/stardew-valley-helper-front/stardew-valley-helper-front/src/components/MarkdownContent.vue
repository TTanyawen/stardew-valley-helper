<template>
  <div
    class="markdown-body"
    :class="variant"
    v-html="html"
  />
</template>

<script setup>
import { computed } from 'vue'
import { renderMarkdown } from '../utils/markdown'

const props = defineProps({
  content: { type: String, default: '' },
  variant: {
    type: String,
    default: 'assistant',
    validator: (v) => ['assistant', 'user'].includes(v),
  },
})

const html = computed(() => renderMarkdown(props.content))
</script>

<style scoped>
.markdown-body {
  font-size: 0.9375rem;
  line-height: 1.7;
  word-break: break-word;
}

.markdown-body :deep(p) {
  margin: 0 0 0.75em;
}

.markdown-body :deep(p:last-child) {
  margin-bottom: 0;
}

.markdown-body :deep(h1),
.markdown-body :deep(h2),
.markdown-body :deep(h3),
.markdown-body :deep(h4) {
  font-weight: 700;
  line-height: 1.35;
  margin: 1em 0 0.5em;
}

.markdown-body :deep(h1) { font-size: 1.35em; }
.markdown-body :deep(h2) { font-size: 1.2em; }
.markdown-body :deep(h3) { font-size: 1.08em; }
.markdown-body :deep(h4) { font-size: 1em; }

.markdown-body :deep(h1:first-child),
.markdown-body :deep(h2:first-child),
.markdown-body :deep(h3:first-child),
.markdown-body :deep(h4:first-child) {
  margin-top: 0;
}

.markdown-body :deep(ul),
.markdown-body :deep(ol) {
  margin: 0.5em 0 0.75em;
  padding-left: 1.4em;
}

.markdown-body :deep(li) {
  margin: 0.25em 0;
}

.markdown-body :deep(li > p) {
  margin: 0.25em 0;
}

.markdown-body :deep(blockquote) {
  margin: 0.75em 0;
  padding: 0.5em 0.9em;
  border-left: 3px solid var(--sky-300);
  background: rgba(186, 230, 253, 0.35);
  border-radius: 0 8px 8px 0;
  color: var(--text-secondary);
}

.markdown-body :deep(blockquote p:last-child) {
  margin-bottom: 0;
}

.markdown-body :deep(code) {
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 0.88em;
  padding: 0.15em 0.4em;
  border-radius: 6px;
  background: rgba(14, 165, 233, 0.12);
  color: #0369a1;
}

.markdown-body :deep(pre) {
  margin: 0.75em 0;
  padding: 12px 14px;
  border-radius: 10px;
  background: #1e3a5f;
  overflow-x: auto;
}

.markdown-body :deep(pre code) {
  padding: 0;
  background: transparent;
  color: #e0f2fe;
  font-size: 0.85em;
  line-height: 1.55;
}

.markdown-body :deep(a) {
  color: var(--sky-500);
  text-decoration: underline;
  text-underline-offset: 2px;
}

.markdown-body :deep(a:hover) {
  color: var(--sky-400);
}

.markdown-body :deep(table) {
  width: 100%;
  margin: 0.75em 0;
  border-collapse: collapse;
  font-size: 0.9em;
  display: block;
  overflow-x: auto;
}

.markdown-body :deep(th),
.markdown-body :deep(td) {
  border: 1px solid var(--border);
  padding: 8px 12px;
  text-align: left;
}

.markdown-body :deep(th) {
  background: var(--sky-100);
  font-weight: 700;
}

.markdown-body :deep(tr:nth-child(even) td) {
  background: rgba(240, 249, 255, 0.6);
}

.markdown-body :deep(hr) {
  border: none;
  border-top: 1px solid var(--border);
  margin: 1em 0;
}

.markdown-body :deep(strong) {
  font-weight: 700;
}

.markdown-body :deep(img) {
  max-width: 100%;
  border-radius: 8px;
  margin: 0.5em 0;
}

/* 用户气泡（蓝色背景）内的 Markdown 样式 */
.markdown-body.user :deep(blockquote) {
  border-left-color: rgba(255, 255, 255, 0.7);
  background: rgba(255, 255, 255, 0.15);
  color: rgba(255, 255, 255, 0.95);
}

.markdown-body.user :deep(code) {
  background: rgba(255, 255, 255, 0.2);
  color: #fff;
}

.markdown-body.user :deep(pre) {
  background: rgba(15, 45, 75, 0.55);
}

.markdown-body.user :deep(pre code) {
  color: #f0f9ff;
}

.markdown-body.user :deep(a) {
  color: #fff;
  font-weight: 600;
}

.markdown-body.user :deep(th) {
  background: rgba(255, 255, 255, 0.2);
  color: #fff;
}

.markdown-body.user :deep(th),
.markdown-body.user :deep(td) {
  border-color: rgba(255, 255, 255, 0.35);
  color: #fff;
}

.markdown-body.user :deep(tr:nth-child(even) td) {
  background: rgba(255, 255, 255, 0.08);
}
</style>
