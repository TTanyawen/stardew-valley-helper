import request from '../utils/request'

export function getAllMessageGroups(userId) {
  return request.get('/msg/getAllMessageGroupByUserId', { params: { userId } })
}

export function getMessageDetails(messageGroupId) {
  return request.get('/msg/getAllMessgaeDetailByMessageId', {
    params: { messageGroupId },
  })
}

export function createMessageGroup(userId, title, messageLevelSystemPrompt = '') {
  return request.get('/msg/createNewMessageGroup', {
    params: { userId, title, messageLevelSystemPrompt },
  })
}

export function setSystemPrompt(messageGroupId, messageLevelSystemPrompt) {
  return request.get('/msg/setMessageLevelSystemPrompt', {
    params: { messageGroupId, messageLevelSystemPrompt },
  })
}
