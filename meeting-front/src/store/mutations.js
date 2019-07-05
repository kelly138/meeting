import { stat } from "fs";

// 部门信息存储
export const setDeps = (state,data) =>{
  state.depItems = data
}

// 会议室信息存储
export const setMeetings = (state,data) =>{
  state.meetingItems = data
}

// 会议室申请记录存储
export const setRecords = (state,data) =>{
  state.recordsItems = data
}

// 存储管理员未审核会议室申请记录
export const setWillReview = (state,data) =>{
  state.willReviewItems = data
}

// 存储管理员已审核的会议室申请记录
export const setReviewed = (state,data) =>{
  state.reviewedItems = data
}

// 存储全部会议室信息
export const setRooms = (state,data) =>{
  state.roomItems = data
}

// // 点击申请后，存储当前会议室信息--------------此思想暂时失败，以后再试
// export const setApplyRooms = (state,data) =>{
//   state.applyRoomItems = data
// }

// 存储当前会议室从现在开始的日期往后，被申请的记录
export const setRoomListRecord = (state,data) =>{
  state.roomListRecordItems = data
}

// 存储当前部门从现在开始的日期往后申请的会议室时间段
export const setDepListRecord = (state,data) =>{
  state.depListRecordItems = data
}

// 存储部门申请记录------部门界面查询会议室申请记录
export const setDepApplyRecords = (state,data) =>{
  state.DepApplyRecordsItems = data
}

// 存储管理员未审核记录的数量
export const setWillReviewNum = (state,data) =>{
  state.WillReviewItems = data
}