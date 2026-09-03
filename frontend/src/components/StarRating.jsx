export default function StarRating({ score }) {
  if (score == null) return null;
  
  return (
    <span style={{ color: '#eab308', fontSize: '1.1rem', letterSpacing: '2px' }}>
      {'★'.repeat(score) + '☆'.repeat(5 - score)}
    </span>
  );
}