// Confirmation popup shown before a delete action.
// Usage: <ConfirmDialog message="Xoa tai khoan nay?" onConfirm={doDelete} onCancel={closeDialog} />
function ConfirmDialog({ message, onConfirm, onCancel }) {
  return (
    <div className="modal-overlay" onClick={onCancel}>
      <div className="modal-box" onClick={(event) => event.stopPropagation()} style={{ maxWidth: 400 }}>
        <p>{message}</p>
        <div className="modal-footer">
          <button type="button" className="button button-secondary" onClick={onCancel}>
            Huy
          </button>
          <button type="button" className="button button-danger" onClick={onConfirm}>
            Xoa
          </button>
        </div>
      </div>
    </div>
  );
}

export default ConfirmDialog;
