import { Link } from "react-router-dom";
import { formatCurrency } from "../../data/storefront";

export default function ProductCard({ product }) {
  return (
    <Link
      to={`/products/${product.id}`}
      aria-label={`Xem chi tiết ${product.name}`}
      className="block h-full"
    >
      <article className="group flex h-full cursor-pointer flex-col overflow-hidden rounded-[30px] border border-white/[0.08] bg-[rgb(41,48,50)] p-4 shadow-[0_20px_70px_rgba(0,0,0,0.25)] transition duration-300 hover:-translate-y-2 hover:border-white/35">
        <div className="flex items-start justify-between gap-4">
          <div>
            <span className="inline-flex rounded-full border border-white/20 bg-white/[0.06] px-3 py-1 text-[11px] font-semibold uppercase tracking-[0.24em] text-[var(--accent)]">
              {product.badge}
            </span>
            <p className="mt-3 text-xs uppercase tracking-[0.24em] text-white/[0.45]">
              {product.category}
            </p>
          </div>
          <p className="text-right text-xs text-white/[0.55]">còn hàng</p>
        </div>

        <div className="mt-5 flex min-h-[220px] items-center justify-center rounded-[26px] border border-white/[0.08] bg-white/[0.05] p-5">
          <img
            src={product.imageUrl}
            alt={product.name}
            className="max-h-[210px] w-full object-contain drop-shadow-[0_24px_50px_rgba(0,0,0,0.38)] transition duration-300 group-hover:scale-[1.04]"
          />
        </div>

        <div className="mt-5 flex flex-1 flex-col">
          <h3 className="font-display text-2xl font-semibold leading-tight text-white">
            {product.name}
          </h3>
          <p className="mt-3 text-sm leading-7 text-white/[0.72]">
            {product.description}
          </p>

          <div className="mt-5 flex flex-wrap gap-2">
            {product.specs?.map((spec) => (
              <span
                key={spec}
                className="rounded-full border border-white/[0.08] bg-black/[0.12] px-3 py-1 text-xs font-medium text-white/75"
              >
                {spec}
              </span>
            ))}
          </div>

          <div className="mt-6">
            <p className="text-xs uppercase tracking-[0.24em] text-white/[0.45]">
              Giá ưu đãi
            </p>
            <p className="mt-2 text-2xl font-bold text-white">
              {formatCurrency(product.price)}
            </p>
            {product.compareAt ? (
              <p className="mt-1 text-sm text-white/[0.45] line-through">
                {formatCurrency(product.compareAt)}
              </p>
            ) : null}
          </div>
        </div>
      </article>
    </Link>
  );
}
