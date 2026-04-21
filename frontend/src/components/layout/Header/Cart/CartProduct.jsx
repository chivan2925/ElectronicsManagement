import { MdDelete } from "react-icons/md";
import { formatCurrency } from "../../../../data/storefront";

export default function CartProduct({
  id,
  name,
  price,
  quantity,
  image,
  onDelete,
  onQuantityChange,
}) {
  const decreaseQuantity = () => {
    onQuantityChange(id, Math.max(1, quantity - 1));
  };

  const increaseQuantity = () => {
    onQuantityChange(id, quantity + 1);
  };

  return (
    <div className="flex items-center gap-3 rounded-2xl border border-white/[0.08] bg-white/[0.03] p-3">
      <img
        src={image}
        alt={name}
        className="h-16 w-16 flex-shrink-0 rounded-lg object-cover"
      />

      <div className="min-w-0 flex-1">
        <h4 className="truncate text-sm font-medium text-white">{name}</h4>
        <p className="mt-1 text-sm font-semibold text-[var(--accent)]">
          {formatCurrency(price)}
        </p>
      </div>

      <div className="flex items-center gap-2">
        <button
          type="button"
          onClick={decreaseQuantity}
          className="inline-flex h-7 w-7 items-center justify-center rounded-md border border-white/[0.2] text-white/80 transition hover:text-[var(--accent)]"
          aria-label="Giảm số lượng"
        >
          -
        </button>
        <span className="w-5 text-center text-sm font-semibold text-white">
          {quantity}
        </span>
        <button
          type="button"
          onClick={increaseQuantity}
          className="inline-flex h-7 w-7 items-center justify-center rounded-md border border-white/[0.2] text-white/80 transition hover:text-[var(--accent)]"
          aria-label="Tăng số lượng"
        >
          +
        </button>
      </div>

      <button
        type="button"
        onClick={() => onDelete(id)}
        className="text-white/50 transition-colors hover:text-red-400"
        aria-label="Xóa sản phẩm"
      >
        <MdDelete size={22} />
      </button>
    </div>
  );
}
