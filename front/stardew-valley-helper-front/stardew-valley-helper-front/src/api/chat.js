/**
 * SSE 流式对话
 * GET /ai/chat?memoryGroupId=&message=
 */
export function chatStream(memoryGroupId, message, { onChunk, onDone, onError }) {
  const params = new URLSearchParams({
    memoryGroupId: String(memoryGroupId),
    message,
  })
  const source = new EventSource(`/api/ai/chat?${params}`)

  source.onmessage = (event) => {
    onChunk?.(event.data)
  }

  source.onerror = () => {
    source.close()
    onDone?.()
  }

  return () => source.close()
}
