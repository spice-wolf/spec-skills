class YAMLError(Exception):
    pass


def _parse_scalar(raw):
    value = raw.strip()
    if not value:
        return ""
    if (value.startswith('"') and value.endswith('"')) or (
        value.startswith("'") and value.endswith("'")
    ):
        return value[1:-1]
    if value in {"true", "True"}:
        return True
    if value in {"false", "False"}:
        return False
    if value in {"null", "None", "~"}:
        return None
    return value


def safe_load(text):
    root = {}
    stack = [(-1, root)]

    for lineno, line in enumerate(text.splitlines(), start=1):
        if not line.strip() or line.lstrip().startswith("#"):
            continue

        indent = len(line) - len(line.lstrip(" "))
        stripped = line.strip()
        if ":" not in stripped:
            raise YAMLError(f"line {lineno}: expected key/value mapping")

        key, raw_value = stripped.split(":", 1)
        key = key.strip()
        if not key:
            raise YAMLError(f"line {lineno}: empty key")

        while stack and indent <= stack[-1][0]:
            stack.pop()
        if not stack:
            raise YAMLError(f"line {lineno}: invalid indentation")

        current = stack[-1][1]
        if raw_value.strip() == "":
            new_dict = {}
            current[key] = new_dict
            stack.append((indent, new_dict))
        else:
            current[key] = _parse_scalar(raw_value)

    return root
