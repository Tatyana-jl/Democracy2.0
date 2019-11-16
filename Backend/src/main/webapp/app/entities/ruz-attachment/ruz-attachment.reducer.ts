import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRuzAttachment, defaultValue } from 'app/shared/model/ruz-attachment.model';

export const ACTION_TYPES = {
  FETCH_RUZATTACHMENT_LIST: 'ruzAttachment/FETCH_RUZATTACHMENT_LIST',
  FETCH_RUZATTACHMENT: 'ruzAttachment/FETCH_RUZATTACHMENT',
  CREATE_RUZATTACHMENT: 'ruzAttachment/CREATE_RUZATTACHMENT',
  UPDATE_RUZATTACHMENT: 'ruzAttachment/UPDATE_RUZATTACHMENT',
  DELETE_RUZATTACHMENT: 'ruzAttachment/DELETE_RUZATTACHMENT',
  RESET: 'ruzAttachment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRuzAttachment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RuzAttachmentState = Readonly<typeof initialState>;

// Reducer

export default (state: RuzAttachmentState = initialState, action): RuzAttachmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RUZATTACHMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RUZATTACHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RUZATTACHMENT):
    case REQUEST(ACTION_TYPES.UPDATE_RUZATTACHMENT):
    case REQUEST(ACTION_TYPES.DELETE_RUZATTACHMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RUZATTACHMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RUZATTACHMENT):
    case FAILURE(ACTION_TYPES.CREATE_RUZATTACHMENT):
    case FAILURE(ACTION_TYPES.UPDATE_RUZATTACHMENT):
    case FAILURE(ACTION_TYPES.DELETE_RUZATTACHMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RUZATTACHMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_RUZATTACHMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RUZATTACHMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_RUZATTACHMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RUZATTACHMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/ruz-attachments';

// Actions

export const getEntities: ICrudGetAllAction<IRuzAttachment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RUZATTACHMENT_LIST,
    payload: axios.get<IRuzAttachment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRuzAttachment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RUZATTACHMENT,
    payload: axios.get<IRuzAttachment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRuzAttachment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RUZATTACHMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRuzAttachment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RUZATTACHMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRuzAttachment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RUZATTACHMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
